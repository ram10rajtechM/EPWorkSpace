/*
 * Copyright © 2016 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.example.relationship;

import javax.inject.Inject;

import io.reactivex.Observable;

import com.elasticpath.rest.definition.charity.CharityFormIdentifier;
import com.elasticpath.rest.definition.charity.OrderToCharityRelationship;
import com.elasticpath.rest.definition.orders.OrderIdentifier;
import com.elasticpath.rest.helix.data.annotation.RequestIdentifier;
import org.apache.log4j.Logger;

/**
 * Implementation of link-to operation of {@link OrderToCharityRelationship}.
 */
public class OrderToCharityRelationshipImpl implements OrderToCharityRelationship.LinkTo {

    @Inject
    @RequestIdentifier
    private OrderIdentifier orderIdentifier;
private static final Logger LOG = Logger.getLogger(OrderToCharityRelationshipImpl.class);
    @Override
    public Observable<CharityFormIdentifier> onLinkTo() {
    LOG.info("relatioship order id "+orderIdentifier.getOrderId());
        return Observable.just(CharityFormIdentifier.builder()
                .withScope(orderIdentifier.getScope())
                .withCharityId(orderIdentifier.getOrderId())
                .build());
    }
}