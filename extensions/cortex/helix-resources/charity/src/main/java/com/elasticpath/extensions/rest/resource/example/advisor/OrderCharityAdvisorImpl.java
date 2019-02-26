/*
 * Copyright © 2017 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.example.advisor;

import javax.inject.Inject;

import io.reactivex.Observable;

import com.elasticpath.extensions.rest.resource.integration.epcommerce.repository.CharityValidationService;
import com.elasticpath.rest.advise.LinkedMessage;
import com.elasticpath.rest.definition.charity.CharityFormIdentifier;
import com.elasticpath.rest.definition.charity.OrderCharityAdvisor;
import com.elasticpath.rest.definition.orders.OrderIdentifier;
import com.elasticpath.rest.helix.data.annotation.RequestIdentifier;
import com.elasticpath.rest.helix.data.annotation.ResourceService;

/**
 * Charity advisor on orders resource.
 */
public final class OrderCharityAdvisorImpl implements OrderCharityAdvisor.ReadLinkedAdvisor {

    @Inject
    @RequestIdentifier
    private OrderIdentifier orderIdentifier;

    @Inject
    @ResourceService
    private CharityValidationService validationService;

    @Override
    public Observable<LinkedMessage<CharityFormIdentifier>> onLinkedAdvise() {
        return validationService.validateCharityAccepted(orderIdentifier);
    }
}