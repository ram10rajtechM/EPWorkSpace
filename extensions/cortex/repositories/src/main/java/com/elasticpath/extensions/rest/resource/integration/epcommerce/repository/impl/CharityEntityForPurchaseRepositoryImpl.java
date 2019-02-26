/*
 * Copyright © 2017 Elastic Path Software Inc. All rights reserved.
 */

package com.elasticpath.extensions.rest.resource.integration.epcommerce.repository.impl;

import com.elasticpath.repository.Repository;
import com.elasticpath.rest.definition.charity.CharityEntity;
import com.elasticpath.rest.definition.charity.CharityForPurchaseIdentifier;
import com.elasticpath.rest.resource.integration.epcommerce.repository.order.OrderRepository;
import com.elasticpath.rest.resource.integration.epcommerce.transform.MoneyTransformer;
import io.reactivex.Single;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Discounts Entity Repository.
 *
 * @param <E> the entity type
 * @param <I> the identifier type
 */
@Component
public class CharityEntityForPurchaseRepositoryImpl<E extends CharityEntity, I extends CharityForPurchaseIdentifier>
		implements Repository<CharityEntity, CharityForPurchaseIdentifier> {

	private OrderRepository orderRepository;
	private MoneyTransformer moneyTransformer;

	@Override
	public Single<CharityEntity> findOne(final CharityForPurchaseIdentifier identifier) {

		String purchaseId = identifier.getPurchase().getPurchaseId().getValue();
		String scope = identifier.getPurchase().getPurchases().getScope().getValue();

		return orderRepository.findByGuidAsSingle(scope, purchaseId)
				.map(order -> moneyTransformer.transformToEntity(order.getSubtotalDiscountMoney(), order.getLocale()))
				.map(costEntity -> CharityEntity.builder()
						.withAmount("100")
						.build());
	}

	@Reference
	public void setCartOrderRepository(final OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Reference
	public void setMoneyTransformer(final MoneyTransformer moneyTransformer) {
		this.moneyTransformer = moneyTransformer;
	}

}