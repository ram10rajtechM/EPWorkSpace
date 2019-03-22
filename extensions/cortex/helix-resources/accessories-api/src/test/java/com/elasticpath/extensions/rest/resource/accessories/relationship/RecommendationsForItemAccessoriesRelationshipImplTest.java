package com.elasticpath.extensions.rest.resource.accessories.relationship;

import com.elasticpath.extensions.rest.resource.relationship.RecommendationsForItemAccessoriesRelationshipImpl;
import com.elasticpath.rest.definition.accessories.ItemRecommendationAccessoriesIdentifier;
import com.elasticpath.rest.definition.items.ItemIdentifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RecommendationsForItemAccessoriesRelationshipImplTest {

    @Mock
    ItemIdentifier itemIdentifier;

    @InjectMocks
    RecommendationsForItemAccessoriesRelationshipImpl fixture;

    @Test
    public void shouldReturnAccessoriesRelationship(){
        ItemRecommendationAccessoriesIdentifier expectedIdentifier = ItemRecommendationAccessoriesIdentifier.builder()
                .withItem(itemIdentifier)
                .build();

        fixture.onLinkTo()
                .test()
                .assertValue(expectedIdentifier);
    }
}
