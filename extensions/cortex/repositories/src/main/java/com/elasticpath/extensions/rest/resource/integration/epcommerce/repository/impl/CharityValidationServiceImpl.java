/*
 * Copyright © 2017 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.integration.epcommerce.repository.impl;

import io.reactivex.Observable;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.elasticpath.extensions.rest.resource.integration.epcommerce.repository.CharityValidationService;
import com.elasticpath.repository.Repository;
import com.elasticpath.rest.advise.LinkedMessage;
import com.elasticpath.rest.definition.charity.CharityEntity;
import com.elasticpath.rest.definition.charity.CharityFormIdentifier;
import com.elasticpath.rest.definition.orders.OrderIdentifier;
import com.elasticpath.rest.id.IdentifierPart;
import com.elasticpath.rest.schema.StructuredMessageTypes;

/**
 * Charity Validation Service Impl.
 */
@Component
public final class CharityValidationServiceImpl implements CharityValidationService {

    @Reference(target = "(name=charityEntityRepositoryImpl)")
    private Repository<CharityEntity, CharityFormIdentifier> repository;



    @Override
    public Observable<LinkedMessage<com.elasticpath.rest.definition.charity.CharityFormIdentifier>> validateCharityAccepted(final OrderIdentifier orderIdentifier) {
        IdentifierPart<String> scope = orderIdentifier.getScope();
        IdentifierPart<String> orderId = orderIdentifier.getOrderId();




        CharityFormIdentifier charityFormIdentifier = CharityFormIdentifier.builder()
                .withScope(scope)
                .withCharityId(orderId)
                .build();

        return repository.findOne(charityFormIdentifier)
                .flatMapObservable(charityEntity -> shouldAddLink(scope, orderId, charityEntity))
                .map(this::buildLinkedMessage);
    }

    private Observable<CharityFormIdentifier> shouldAddLink(final IdentifierPart<String> scope,
                                                            final IdentifierPart<String> orderId,
                                                            final CharityEntity charityEntity) {
        if (charityEntity.isAccepted()) {
            return Observable.empty();
        }
        return Observable.just(CharityFormIdentifier.builder()
                .withScope(scope)
                .withCharityId(orderId)
                .build());
    }

    private LinkedMessage<CharityFormIdentifier> buildLinkedMessage(
            final CharityFormIdentifier charityFormIdentifier) {

        return LinkedMessage.<CharityFormIdentifier>builder()
                .withType(StructuredMessageTypes.NEEDINFO)
                .withDebugMessage("Charity must be confirmed before making a purchase.")
                .withId("charity.not.confirmed")
                .withLinkedIdentifier(charityFormIdentifier)
                .build();
    }
}