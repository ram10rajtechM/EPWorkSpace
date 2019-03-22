package com.elasticpath.extensions.rest.resource.prototype;

import com.elasticpath.rest.definition.accessories.ItemRecommendationAccessoriesIdentifier;
import com.elasticpath.rest.definition.accessories.ItemRecommendationAccessoriesResource;
import com.elasticpath.rest.definition.accessories.PaginatedRecommendationsAccessoriesIdentifier;
import com.elasticpath.rest.helix.data.annotation.RequestIdentifier;
import com.elasticpath.rest.id.type.IntegerIdentifier;
import io.reactivex.Single;

import javax.inject.Inject;

public class ReadItemRecommendationAccessoriesPrototype implements ItemRecommendationAccessoriesResource.Read {

    private static final Integer FIRST_PAGE = 1;
    private final ItemRecommendationAccessoriesIdentifier itemRecommendationAccessoriesIdentifier;

    /**
     * Constructor.
     *
     * @param itemRecommendationAccessoriesIdentifier itemRecommendation Accessories Identifier
     */
    @Inject
    public ReadItemRecommendationAccessoriesPrototype(@RequestIdentifier final ItemRecommendationAccessoriesIdentifier itemRecommendationAccessoriesIdentifier) {
        this.itemRecommendationAccessoriesIdentifier = itemRecommendationAccessoriesIdentifier;
    }


    @Override
    public Single<PaginatedRecommendationsAccessoriesIdentifier> onRead() {
        return Single.just(PaginatedRecommendationsAccessoriesIdentifier.builder()
                .withItemRecommendationAccessories(itemRecommendationAccessoriesIdentifier)
                .withPageId(IntegerIdentifier.of(FIRST_PAGE))
                .build());
    }
}
