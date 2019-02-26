/*
 * Copyright © 2017 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.integration.epcommerce.repository;

import io.reactivex.Observable;

import com.elasticpath.rest.advise.LinkedMessage;
import com.elasticpath.rest.definition.charity.CharityFormIdentifier;
import com.elasticpath.rest.definition.orders.OrderIdentifier;

/**
 * Validation service for checking if the charity has been accepted.
 */
public interface CharityValidationService {

    /**
     * Check if the charity has been accepted.
     *
     * @param orderIdentifier orderIdentifier
     * @return a message if charity is not accepted
     */
    Observable<LinkedMessage<com.elasticpath.rest.definition.charity.CharityFormIdentifier>> validateCharityAccepted(OrderIdentifier orderIdentifier);
}