/*
 * Copyright © 2017 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.example.advisor;

import javax.inject.Inject;

import io.reactivex.Observable;

import com.elasticpath.extensions.rest.resource.integration.epcommerce.repository.CharityValidationService;
import com.elasticpath.rest.advise.LinkedMessage;
import com.elasticpath.rest.definition.charity.CharityFormIdentifier;
import com.elasticpath.rest.definition.charity.PurchaseFormCharityAdvisor;
import com.elasticpath.rest.definition.purchases.CreatePurchaseFormIdentifier;
import com.elasticpath.rest.helix.data.annotation.RequestIdentifier;
import com.elasticpath.rest.helix.data.annotation.ResourceService;

/**
 * Charity advisor on purchase form.
 */
public final class PurchaseFormAdvisorImpl implements PurchaseFormCharityAdvisor.LinkedFormAdvisor {

    @Inject
    @RequestIdentifier
    private CreatePurchaseFormIdentifier createPurchaseFormIdentifier;

    @Inject
    @ResourceService
    private CharityValidationService validationService;

    @Override
    public Observable<LinkedMessage<CharityFormIdentifier>> onLinkedAdvise() {
        return validationService.validateCharityAccepted(createPurchaseFormIdentifier.getOrder());
    }
}