/*
 * Copyright © 2016 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.example.permissions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import io.reactivex.Observable;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.elasticpath.rest.definition.charity.CharityFormIdentifier;
import com.elasticpath.rest.id.Identifier;
import com.elasticpath.rest.id.transform.IdentifierTransformer;
import com.elasticpath.rest.id.transform.IdentifierTransformerProvider;
import com.elasticpath.rest.id.type.StringIdentifier;
import com.elasticpath.rest.id.util.Base32Util;
import com.elasticpath.rest.identity.ScopePrincipal;
import com.elasticpath.rest.identity.UserPrincipal;
import com.elasticpath.rest.resource.integration.epcommerce.repository.cartorder.CartOrderRepository;

@RunWith(MockitoJUnitRunner.class)
public class CharityIdParameterStrategyTest {

    private static final String TEST_REALM = "testRealm";
    private static final String DECODED_CHARITY_ID = "7F4E992F-9CFC-E648-BA11-DF1D5B23968F";


    @InjectMocks
    private CharityIdParameterStrategy fixture;

    @Mock
    CartOrderRepository cartOrderRepository;

    @Mock
    private IdentifierTransformerProvider identifierTransformerProvider;
    @Mock
    private IdentifierTransformer<Identifier> identifierTransformer;


    /**
     * Test get charity id parameter.
     */
    @Test

    public void testGetCharityIdParameterValue() {
        final Identifier charityIdentifier = StringIdentifier.of(DECODED_CHARITY_ID);
        PrincipalCollection principles = new SimplePrincipalCollection(
                new SimplePrincipalCollection(
                        Arrays.asList(
                                new UserPrincipal(DECODED_CHARITY_ID),
                                new ScopePrincipal(TEST_REALM)), "test-realm")
        );
        when(cartOrderRepository.findCartOrderGuidsByCustomerAsObservable(TEST_REALM, DECODED_CHARITY_ID))
                .thenReturn(Observable.just(DECODED_CHARITY_ID));
        when(identifierTransformerProvider.forUriPart(CharityFormIdentifier.CHARITY_ID)).thenReturn(identifierTransformer);
        when(identifierTransformer.identifierToUri(any())).thenReturn(Base32Util.encode(DECODED_CHARITY_ID));

        String expectedCharityIds = Base32Util.encode(DECODED_CHARITY_ID);
        String charityIds = fixture.getParameterValue(principles);

        assertEquals(expectedCharityIds, charityIds);
        verify(identifierTransformerProvider).forUriPart(CharityFormIdentifier.CHARITY_ID);
        verify(identifierTransformer).identifierToUri(charityIdentifier);

    }

}