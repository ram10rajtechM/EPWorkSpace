package com.elasticpath.extensions.service.charity.impl;

import com.elasticpath.extensions.domain.charity.CharityFlag;
import com.elasticpath.extensions.service.charity.CharityFlagService;
import com.elasticpath.persistence.api.EpPersistenceException;
import com.elasticpath.persistence.api.PersistenceEngine;

import java.util.List;

/**
 * Implementation of TnC service methods.
 */
public class CharityFlagServiceImpl implements CharityFlagService {
    private PersistenceEngine persistenceEngine;


    @Override
    public CharityFlag add(final CharityFlag charityFlag) {
        CharityFlag newCharityFlag;

        try {
            newCharityFlag = this.persistenceEngine.saveOrUpdate(charityFlag);
        } catch (final Exception ex) {
            throw new EpPersistenceException("Exception on adding CharityFlag.", ex);
        }

        return newCharityFlag;
    }

    @Override
    public void delete(final CharityFlag charityFlag) {
        getPersistenceEngine().delete(charityFlag);
        getPersistenceEngine().flush();
    }

    @Override
    public CharityFlag findByCode(final String charity) {
        final List<CharityFlag> charityFlagnFlags =
                this.persistenceEngine.retrieveByNamedQuery("FIND_TNC_BY_CHARITY", charity);

        if (!charityFlagnFlags.isEmpty()) {
            return charityFlagnFlags.get(0);
        }
        return null;
    }

    @Override
    public List<CharityFlag> findAllCharityFlags() {
        return this.getPersistenceEngine().retrieveByNamedQuery("FIND_ALL_CHARITY");
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