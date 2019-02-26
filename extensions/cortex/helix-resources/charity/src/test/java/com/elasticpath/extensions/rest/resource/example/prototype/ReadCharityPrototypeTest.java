/*
 * Copyright © 2016 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.example.prototype;


import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.elasticpath.repository.Repository;
import com.elasticpath.rest.definition.charity.CharityFormIdentifier;

@RunWith(MockitoJUnitRunner.class)
public class ReadCharityPrototypeTest {

    @InjectMocks
    private ReadCharityPrototype readCharityPrototype;

    @Mock
    private Repository charityRepository;

    @Mock
    private CharityFormIdentifier charityFormIdentifier;


    @Test
    public void shouldReturnCharity() {
        readCharityPrototype.onRead();

        verify(charityRepository).findOne(charityFormIdentifier);
    }


}