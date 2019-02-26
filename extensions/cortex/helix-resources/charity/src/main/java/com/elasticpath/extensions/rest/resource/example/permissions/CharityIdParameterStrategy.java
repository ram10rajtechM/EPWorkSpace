/*
 * Copyright © 2016 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.example.permissions;

import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.shiro.subject.PrincipalCollection;

import com.elasticpath.rest.authorization.parameter.AbstractCollectionValueStrategy;
import com.elasticpath.rest.definition.charity.CharityFormIdentifier;
import com.elasticpath.rest.id.Identifier;
import com.elasticpath.rest.id.transform.IdentifierTransformer;
import com.elasticpath.rest.id.transform.IdentifierTransformerProvider;
import com.elasticpath.rest.id.type.StringIdentifier;
import com.elasticpath.rest.identity.util.PrincipalsUtil;
import com.elasticpath.rest.resource.integration.epcommerce.repository.cartorder.CartOrderRepository;

/**
 * Strategy to look up permission for Charity resource, which uses cartOrderIds.
 */
@Singleton
@Named
public final class CharityIdParameterStrategy extends AbstractCollectionValueStrategy {

    @Inject
    private CartOrderRepository cartOrderRepository;

    @Inject
    private IdentifierTransformerProvider identifierTransformerProvider;


    @Override
    protected Collection<String> getParameterValues(final PrincipalCollection principals) {
        String scope = PrincipalsUtil.getScope(principals);
        String userId = PrincipalsUtil.getUserIdentifier(principals);
        IdentifierTransformer<Identifier> transformer = identifierTransformerProvider.forUriPart(CharityFormIdentifier.CHARITY_ID);

        return cartOrderRepository.findCartOrderGuidsByCustomerAsObservable(scope, userId)
                .map(charityId -> transformer.identifierToUri(StringIdentifier.of(charityId)))
                .toList()
                .blockingGet();
    }
}