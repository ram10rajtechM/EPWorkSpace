/*
 * Copyright © 2016 Elastic Path Software Inc. All rights reserved.
 */
package com.elasticpath.extensions.rest.resource.example.prototype;


import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import io.reactivex.Completable;
import io.reactivex.Single;

import com.elasticpath.repository.Repository;
import com.elasticpath.rest.definition.charity.CharityEntity;
import com.elasticpath.rest.definition.charity.CharityFormIdentifier;


@RunWith(MockitoJUnitRunner.class)
public class UpdateCharityPrototypeTest {

    @InjectMocks
    private UpdateCharityPrototype updateCharityPrototype;

    @Mock
    private Repository charityRepository;

    @Mock
    private CharityFormIdentifier charityFormIdentifier;

    @Mock
    private CharityEntity charityEntity;


    @Test
    public void shouldUpdateCharityFromEntity() {
        when(charityRepository.update(charityEntity, charityFormIdentifier))
                .thenReturn(Single.just("this").toCompletable());

        assertThat(updateCharityPrototype.onUpdate(), instanceOf(Completable.class));
        verify(charityRepository).update(charityEntity, charityFormIdentifier);

    }

}