package com.elasticpath.extensions.service.charity.impl;

import com.elasticpath.extensions.domain.charity.CartOrderCharityFlag;
import com.elasticpath.extensions.domain.charity.CharityFlag;
import com.elasticpath.extensions.service.charity.CartOrderCharityFlagService;
import com.elasticpath.extensions.service.charity.CharityFlagService;
import com.elasticpath.persistence.api.EpPersistenceException;
import com.elasticpath.persistence.api.PersistenceEngine;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Implementation of TnC service methods.
 */
public class CartOrderCharityFlagServiceImpl implements CartOrderCharityFlagService {
    private PersistenceEngine persistenceEngine;
    private static final Logger LOG = Logger.getLogger(CartOrderCharityFlagServiceImpl.class);

    @Override
    public CartOrderCharityFlag add(final CartOrderCharityFlag cartOrderCharityFlag) {
        CartOrderCharityFlag newCartOrderCharityFlag;

        try {
            newCartOrderCharityFlag = this.persistenceEngine.saveOrUpdate(cartOrderCharityFlag);
        } catch (final Exception ex) {
            throw new EpPersistenceException("Exception on adding CharityFlag.", ex);
        }

        return newCartOrderCharityFlag;
    }

    @Override
    public void delete(final CartOrderCharityFlag cartOrderCharityFlag) {
        getPersistenceEngine().delete(cartOrderCharityFlag);
        getPersistenceEngine().flush();
    }

    @Override
    public CartOrderCharityFlag findByCartOrderGuid(final String guid) {
        LOG.info("before cart order Guid : " +guid);
        final List<CartOrderCharityFlag> cartOrderCharityFlags =
                this.persistenceEngine.retrieveByNamedQuery("FIND_TNC_BY_CART_ORDER_CHARITY", guid);
        LOG.info("After cart order Guid : " +guid);

        if (!cartOrderCharityFlags.isEmpty()) {
            return cartOrderCharityFlags.get(0);
        }
        return null;
    }

    @Override
    public List<CartOrderCharityFlag> findAllCharityCartOrderFlags() {
        return this.getPersistenceEngine().retrieveByNamedQuery("FIND_ALL_CART_ORDER_CHARITY");
    }

    /**
     * @return PersistenceEngine
     */
    public PersistenceEngine getPersistenceEngine() {
        return persistenceEngine;
    }

    /**
     * @param persistenceEngine PersistenceEngine
     */
    public void setPersistenceEngine(final PersistenceEngine persistenceEngine) {
        this.persistenceEngine = persistenceEngine;
    }

}