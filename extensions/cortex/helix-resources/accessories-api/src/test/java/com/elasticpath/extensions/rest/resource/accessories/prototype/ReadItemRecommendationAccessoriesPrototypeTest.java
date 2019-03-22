package com.elasticpath.extensions.rest.resource.accessories.prototype;

import com.elasticpath.extensions.rest.resource.prototype.ReadItemRecommendationAccessoriesPrototype;
import com.elasticpath.rest.definition.accessories.ItemRecommendationAccessoriesIdentifier;
import com.elasticpath.rest.definition.accessories.PaginatedRecommendationsAccessoriesIdentifier;
import com.elasticpath.rest.id.type.IntegerIdentifier;
import io.reactivex.Single;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReadItemRecommendationAccessoriesPrototypeTest {
    private static final Integer FIRST_PAGE = 1;
    @InjectMocks
    ReadItemRecommendationAccessoriesPrototype readItemRecommendationAccessoriesPrototype;

    @Mock
    ItemRecommendationAccessoriesIdentifier itemRecommendationAccessoriesIdentifier;

    @Test
    public void shouldReturnItemRecommendationAccessories() {
        Single<PaginatedRecommendationsAccessoriesIdentifier> paginatedRecommendationsAccessoriesIdentifier = Single.just(PaginatedRecommendationsAccessoriesIdentifier.builder()
                .withItemRecommendationAccessories(itemRecommendationAccessoriesIdentifier)
                .withPageId(IntegerIdentifier.of(FIRST_PAGE))
                .build());
        Single<PaginatedRecommendationsAccessoriesIdentifier> result = readItemRecommendationAccessoriesPrototype.onRead();
        Assertions.assertThat(result.blockingGet().getPageId()).isEqualTo(paginatedRecommendationsAccessoriesIdentifier.blockingGet().getPageId());
    }

}
