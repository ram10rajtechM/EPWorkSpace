package com.elasticpath.extensions.rest.resource.relationship;

import com.elasticpath.rest.definition.accessories.ItemRecommendationAccessoriesIdentifier;
import com.elasticpath.rest.definition.accessories.RecommendationsForItemAccessoriesRelationship;
import com.elasticpath.rest.definition.items.ItemIdentifier;
import com.elasticpath.rest.helix.data.annotation.RequestIdentifier;
import io.reactivex.Observable;

import javax.inject.Inject;

public class RecommendationsForItemAccessoriesRelationshipImpl implements RecommendationsForItemAccessoriesRelationship.LinkTo {

    private final ItemIdentifier itemIdentifier;

    /**
     * Constructor.
     *
     * @param itemIdentifier item identifier
     */
    @Inject
    public RecommendationsForItemAccessoriesRelationshipImpl(@RequestIdentifier final ItemIdentifier itemIdentifier) {
        this.itemIdentifier = itemIdentifier;
    }

    @Override
    public Observable<ItemRecommendationAccessoriesIdentifier> onLinkTo() {
        return Observable.just(ItemRecommendationAccessoriesIdentifier.builder()
                .withItem(itemIdentifier)
                .build());
    }
}
