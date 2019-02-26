/*
 * Copyright © 2016 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.example.prototype;

import javax.inject.Inject;

import io.reactivex.Single;

import com.elasticpath.repository.Repository;
import com.elasticpath.rest.definition.charity.CharityEntity;
import com.elasticpath.rest.definition.charity.CharityFormIdentifier;
import com.elasticpath.rest.definition.charity.CharityFormResource;
import com.elasticpath.rest.helix.data.annotation.RequestIdentifier;
import com.elasticpath.rest.helix.data.annotation.ResourceRepository;

/**
 * Prototype for read operation of {@link CharityFormResource}.
 */
public class ReadCharityPrototype implements CharityFormResource.Read {

    @Inject
    @RequestIdentifier
    private CharityFormIdentifier charityFormIdentifier;

    @Inject
    @ResourceRepository
    private Repository<CharityEntity, CharityFormIdentifier> repository;


    @Override
    public Single<CharityEntity> onRead() {
        return repository.findOne(charityFormIdentifier);
    }
}