/*
 * Copyright © 2017 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.example.prototype;

import com.elasticpath.repository.Repository;
import com.elasticpath.rest.definition.charity.CharityEntity;
import com.elasticpath.rest.definition.charity.CharityForPurchaseIdentifier;
import com.elasticpath.rest.definition.charity.CharityForPurchaseResource;
import com.elasticpath.rest.helix.data.annotation.RequestIdentifier;
import com.elasticpath.rest.helix.data.annotation.ResourceRepository;
import io.reactivex.Single;

import javax.inject.Inject;

/**
 * Charity for cart prototype for Read operation.
 */
public class ReadCharityForPurchasePrototype implements CharityForPurchaseResource.Read {

	private final CharityForPurchaseIdentifier charityForPurchaseIdentifier;
	private final Repository<CharityEntity, CharityForPurchaseIdentifier> repository;

	/**
	 * Constructor.
	 *
	 * @param charityForPurchaseIdentifier	discountForCartIdentifier
	 * @param repository      			repository
	 */
	@Inject
	public ReadCharityForPurchasePrototype(@RequestIdentifier final CharityForPurchaseIdentifier charityForPurchaseIdentifier,
                                           @ResourceRepository final Repository<CharityEntity, CharityForPurchaseIdentifier> repository) {
		this.charityForPurchaseIdentifier = charityForPurchaseIdentifier;
		this.repository = repository;
	}

	@Override
	public Single<CharityEntity> onRead() {
		return repository.findOne(charityForPurchaseIdentifier);
	}
}
