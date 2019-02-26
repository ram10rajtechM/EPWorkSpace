/*
 * Copyright © 2017 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.example.relationship;

import com.elasticpath.rest.definition.charity.CharityForPurchaseIdentifier;
import com.elasticpath.rest.definition.charity.CharityToPurchaseRelationship;
import com.elasticpath.rest.definition.discounts.DiscountForPurchaseIdentifier;
import com.elasticpath.rest.definition.discounts.DiscountToPurchaseRelationship;
import com.elasticpath.rest.definition.purchases.PurchaseIdentifier;
import com.elasticpath.rest.helix.data.annotation.RequestIdentifier;
import io.reactivex.Observable;

import javax.inject.Inject;

/**
 * Charity from Purchase link.
 */
public class CharityFromPurchaseRelationshipImpl implements CharityToPurchaseRelationship.LinkFrom {

	private final PurchaseIdentifier purchaseIdentifier;

	/**
	 * Constructor.
	 *
	 * @param purchaseIdentifier	purchaseIdentifier
	 */
	@Inject
	public CharityFromPurchaseRelationshipImpl(@RequestIdentifier final PurchaseIdentifier purchaseIdentifier) {
		this.purchaseIdentifier = purchaseIdentifier;
	}

	@Override
	public Observable<CharityForPurchaseIdentifier> onLinkFrom() {
		return Observable.just(CharityForPurchaseIdentifier.builder()
				.withPurchase(purchaseIdentifier)
				.build());
	}
}
