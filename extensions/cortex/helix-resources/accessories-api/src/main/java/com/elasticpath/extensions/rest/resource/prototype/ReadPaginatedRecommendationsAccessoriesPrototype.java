package com.elasticpath.extensions.rest.resource.prototype;

import com.elasticpath.repository.PaginationRepository;
import com.elasticpath.rest.definition.accessories.PaginatedRecommendationsAccessoriesIdentifier;
import com.elasticpath.rest.definition.accessories.PaginatedRecommendationsAccessoriesResource;
import com.elasticpath.rest.definition.items.ItemIdentifier;
import com.elasticpath.rest.helix.data.annotation.RequestIdentifier;
import com.elasticpath.rest.helix.data.annotation.ResourceRepository;
import com.elasticpath.rest.pagination.PaginationEntity;
import com.elasticpath.rest.pagination.PagingLink;
import io.reactivex.Observable;
import io.reactivex.Single;

import javax.inject.Inject;

public class ReadPaginatedRecommendationsAccessoriesPrototype implements PaginatedRecommendationsAccessoriesResource.Pageable {

    private final PaginatedRecommendationsAccessoriesIdentifier paginatedRecommendationsAccessoriesIdentifier;

    private final PaginationRepository<PaginatedRecommendationsAccessoriesIdentifier, ItemIdentifier> repository;


    /**
     * Constructor.
     *
     * @param paginatedRecommendationsAccessoriesIdentifier paginated recommendation identifier
     * @param repository repository to get items
     */
    @Inject
    public ReadPaginatedRecommendationsAccessoriesPrototype(
            @RequestIdentifier final PaginatedRecommendationsAccessoriesIdentifier paginatedRecommendationsAccessoriesIdentifier,
            @ResourceRepository final PaginationRepository<PaginatedRecommendationsAccessoriesIdentifier, ItemIdentifier> repository) {

        this.paginatedRecommendationsAccessoriesIdentifier = paginatedRecommendationsAccessoriesIdentifier;
        this.repository = repository;
    }

    @Override
    public Single<PaginationEntity> onRead() {
        return repository.getPaginationInfo(paginatedRecommendationsAccessoriesIdentifier);
    }

    @Override
    public Observable<ItemIdentifier> elements() {
        return repository.getElements(paginatedRecommendationsAccessoriesIdentifier);
    }

    @Override
    public Observable<PagingLink<PaginatedRecommendationsAccessoriesIdentifier>> pagingLinks() {
        return repository.getPagingLinks(paginatedRecommendationsAccessoriesIdentifier);
    }
}
