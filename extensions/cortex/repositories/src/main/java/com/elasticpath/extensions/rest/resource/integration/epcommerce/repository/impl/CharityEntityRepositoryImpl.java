/*
 * Copyright © 2016 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.integration.epcommerce.repository.impl;


import com.elasticpath.domain.cartorder.CartOrder;
import com.elasticpath.extensions.domain.cartorder.ExtCartOrder;
import com.elasticpath.extensions.domain.charity.CartOrderCharityFlag;
import com.elasticpath.extensions.domain.charity.impl.CartOrderCharityFlagImpl;
import com.elasticpath.repository.Repository;
import com.elasticpath.rest.cache.CacheResult;
import com.elasticpath.rest.definition.charity.CharityEntity;
import com.elasticpath.rest.definition.charity.CharityFormIdentifier;
import com.elasticpath.rest.resource.integration.epcommerce.repository.calc.TotalsCalculator;
import com.elasticpath.rest.resource.integration.epcommerce.repository.cartorder.CartOrderRepository;
import com.elasticpath.settings.SettingsReader;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.math.BigDecimal;
import java.util.Map;


/**
 * Charity Entity Repository.
 *
 * @param <E> the entity type
 * @param <I> the identifier type
 */
@Component(property = "name=charityEntityRepositoryImpl")
public class CharityEntityRepositoryImpl<E extends CharityEntity, I extends CharityFormIdentifier>
        implements Repository<CharityEntity, CharityFormIdentifier> {
    private static final String ACCEPTED_CHARITY_ATTRIBUTE_KEY = "CP_BE_NOTIFIED";
    private static final String CHARITY_KEY = "Heart Foundation";
    private static final String CHARITY_PERCENTAGE = "COMMERCE/SYSTEM/CHARITY/Percentage";
    @Reference
    private CartOrderRepository cartOrderRepository;

    @Reference
    private TotalsCalculator totalsCalculator;

    private static final Logger LOG = Logger.getLogger(CharityEntityRepositoryImpl.class);

    @Reference
    private SettingsReader settingsReader;


    /*start*/

    @Override
    public Completable update(final CharityEntity entity, final CharityFormIdentifier identifier) {
        return getOrder(identifier)
                .flatMap(order -> updateOrder(entity, order))
                .toCompletable();
    }

    private Single<CartOrder> updateOrder(final CharityEntity entity, final ExtCartOrder cartOrder) {

        Map<String, CartOrderCharityFlag> cartOrderCharityFlags = cartOrder.getCartOrderCharityFlags();
        CartOrderCharityFlag charityFlag;
        if (cartOrderCharityFlags.containsKey(CHARITY_KEY)) {
            charityFlag = cartOrderCharityFlags.get(CHARITY_KEY);

        } else {
            charityFlag = new CartOrderCharityFlagImpl();
            charityFlag.setCharity(CHARITY_KEY);
        }

        charityFlag.setAccepted(entity.isAccepted());


        //String orderId = identifier.getCharityId().getValue();
        //String storeId = identifier.getScope().getValue();
        // BigDecimal CharityAmount = getCharityAmountCal(totalsCalculator.calculateSubTotalForCartOrderSingle(storeId,orderId).blockingGet().getAmount());
        LOG.info("My amount is calculates : " + entity.getAmount());
        LOG.info("setAccepted : " + entity.isAccepted());
        LOG.info("CHARITY_KEY" + CHARITY_KEY);
        charityFlag.setAmount(entity.getAmount());
        charityFlag.setAccepted(entity.isAccepted());
        cartOrderCharityFlags.put(CHARITY_KEY, charityFlag);
        cartOrder.setCartOrderCharityFlags(cartOrderCharityFlags);
        return cartOrderRepository.saveCartOrderAsSingle(cartOrder);
    }

    /* calcualte the Charity amount */
    private BigDecimal getCharityAmountCal(final BigDecimal total) {
        String settingValue = settingsReader.getSettingValue(CHARITY_PERCENTAGE).getValue();
        LOG.info("Configuration value" + settingValue);
        BigDecimal multiplayedCharity = total.multiply(new BigDecimal(settingValue));
        BigDecimal dividedCharity = multiplayedCharity.divide(new BigDecimal(100));
        return dividedCharity;
    }

    @Override
    @CacheResult
    public Single<CharityEntity> findOne(final CharityFormIdentifier identifier) {
        String orderId = identifier.getCharityId().getValue();
        LOG.info("Cart id + : " + orderId);
        String storeId = identifier.getScope().getValue();
        BigDecimal CharityAmount = getCharityAmountCal(totalsCalculator.calculateSubTotalForCartOrderSingle(storeId, orderId).blockingGet().getAmount());
        return Single.just(CharityEntity.builder()
                .withAccepted(false)
                .withMessage("Do you confirm that this purchase is for charity?")
                .withAmount(CharityAmount.toString())
                .build());
    }

    private boolean isCartOrderCharityAccepted(final ExtCartOrder cartOrder) {
        return cartOrder.getCartOrderCharityFlags().containsKey(CHARITY_KEY)
                && cartOrder.getCartOrderCharityFlags().get(CHARITY_KEY).isAccepted();
    }

    private Single<ExtCartOrder> getOrder(final CharityFormIdentifier identifier) {
        LOG.info("Ext Cart Object : "+ cartOrderRepository.findByGuidAsSingle(identifier.getScope().getValue(),
                identifier.getCharityId().getValue()).blockingGet());
        ExtCartOrder cartOrder = (ExtCartOrder) cartOrderRepository.findByGuidAsSingle(identifier.getScope().getValue(),
                identifier.getCharityId().getValue()).blockingGet();
        return Single.just(cartOrder);
    }


}