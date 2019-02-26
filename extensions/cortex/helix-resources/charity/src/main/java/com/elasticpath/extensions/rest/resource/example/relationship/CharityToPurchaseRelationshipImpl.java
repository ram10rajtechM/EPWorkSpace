/*
 * Copyright © 2017 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.example.relationship;

import com.elasticpath.rest.definition.charity.CharityForPurchaseIdentifier;
import com.elasticpath.rest.definition.charity.CharityToPurchaseRelationship;
import com.elasticpath.rest.definition.purchases.PurchaseIdentifier;
import com.elasticpath.rest.helix.data.annotation.RequestIdentifier;
import io.reactivex.Observable;

import javax.inject.Inject;

/**
 * Charity to Purchase link.
 */
public class CharityToPurchaseRelationshipImpl implements CharityToPurchaseRelationship.LinkTo {

	private final CharityForPurchaseIdentifier charityForPurchaseIdentifier;

	/**
	 * Constructor.
	 *
	 * @param charityForPurchaseIdentifier	charityForPurchaseIdentifier
	 */
	@Inject
	public CharityToPurchaseRelationshipImpl(@RequestIdentifier final CharityForPurchaseIdentifier charityForPurchaseIdentifier) {
		this.charityForPurchaseIdentifier = charityForPurchaseIdentifier;
	}

	@Override
	public Observable<PurchaseIdentifier> onLinkTo() {
		return Observable.just(charityForPurchaseIdentifier.getPurchase());
	}
}
