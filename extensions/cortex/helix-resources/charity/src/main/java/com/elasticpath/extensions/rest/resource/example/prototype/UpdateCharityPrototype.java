/*
 * Copyright © 2016 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.example.prototype;

import javax.inject.Inject;

import io.reactivex.Completable;

import com.elasticpath.repository.Repository;
import com.elasticpath.rest.definition.charity.CharityEntity;
import com.elasticpath.rest.definition.charity.CharityFormIdentifier;
import com.elasticpath.rest.definition.charity.CharityFormResource;
import com.elasticpath.rest.helix.data.annotation.RequestForm;
import com.elasticpath.rest.helix.data.annotation.RequestIdentifier;
import com.elasticpath.rest.helix.data.annotation.ResourceRepository;
import org.apache.log4j.Logger;

/**
 * Prototype for update operation of {@link CharityFormResource}.
 */
public class UpdateCharityPrototype implements CharityFormResource.Update {


    @Inject
    @RequestForm
    private CharityEntity charityEntity;

    @Inject
    @RequestIdentifier
    private CharityFormIdentifier charityFormIdentifier;

    @Inject
    @ResourceRepository
    private Repository<CharityEntity, CharityFormIdentifier> repository;

    private static final Logger LOG = Logger.getLogger(UpdateCharityPrototype.class);

    @Override
    public Completable onUpdate() {
        LOG.info("update funcationlaity is called");
        LOG.info(charityEntity.getAmount());
        return repository.update(charityEntity, charityFormIdentifier);
    }
}