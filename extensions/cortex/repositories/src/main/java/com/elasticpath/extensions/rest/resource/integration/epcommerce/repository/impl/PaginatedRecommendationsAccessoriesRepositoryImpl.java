/*
 * Copyright © 2017 Elastic Path Software Inc. All rights reserved.
 */

package com.elasticpath.extensions.rest.resource.integration.epcommerce.repository.impl;

import com.elasticpath.domain.catalog.ProductSku;
import com.elasticpath.repository.PaginationRepository;
import com.elasticpath.rest.definition.accessories.ItemRecommendationAccessoriesIdentifier;
import com.elasticpath.rest.definition.accessories.PaginatedRecommendationsAccessoriesIdentifier;
import com.elasticpath.rest.definition.items.ItemIdentifier;
import com.elasticpath.rest.definition.items.ItemsIdentifier;
import com.elasticpath.rest.id.IdentifierPart;
import com.elasticpath.rest.id.transform.IdentifierTransformerProvider;
import com.elasticpath.rest.id.type.CompositeIdentifier;
import com.elasticpath.rest.id.util.CompositeIdUtil;
import com.elasticpath.rest.pagination.PaginationEntity;
import com.elasticpath.rest.pagination.PagingLink;
import com.elasticpath.rest.resource.integration.epcommerce.repository.item.ItemRepository;
import com.elasticpath.rest.resource.integration.epcommerce.repository.item.recommendations.ItemRecommendationsRepository;
import com.elasticpath.rest.resource.integration.epcommerce.repository.pagination.PaginatedResult;
import com.elasticpath.rest.resource.integration.epcommerce.repository.store.StoreRepository;
import io.reactivex.Observable;
import io.reactivex.Single;
//import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

/**
 * Paginated links entity repo for the recommendation group.
 *
 * @param <R> paginated recommendations identifier
 * @param <I> item identifier
 */
@Component(service = PaginationRepository.class)
public class PaginatedRecommendationsAccessoriesRepositoryImpl<R extends PaginatedRecommendationsAccessoriesIdentifier, I extends ItemIdentifier>
		extends PaginationRepository<PaginatedRecommendationsAccessoriesIdentifier, ItemIdentifier> {

	private ItemRepository itemRepository;
	private StoreRepository storeRepository;
	private ItemRecommendationsRepository itemRecommendationsRepository;
	private IdentifierTransformerProvider identifierTransformerProvider;

	//private static final Logger LOG = Logger.getLogger(PaginatedRecommendationsAccessoriesRepositoryImpl.class);

	@Override
	public Single<PaginationEntity> getPaginationInfo(final PaginatedRecommendationsAccessoriesIdentifier identifier) {
		return getRecommendedItemsFromGroup(identifier)
				.flatMap(this::convertPaginationResultToPaginationEntity);
	}


	@Override
	public Observable<ItemIdentifier> getElements(final PaginatedRecommendationsAccessoriesIdentifier identifier) {
		ItemsIdentifier items = identifier.getItemRecommendationAccessories().getItem().getItems();
		return getRecommendedItemsFromGroup(identifier)
				.flatMapObservable(paginatedResult -> Observable.fromIterable(paginatedResult.getResultIds()))
				.map(itemId -> ItemIdentifier.builder()
						.withItemId(CompositeIdentifier.of(CompositeIdUtil.decodeCompositeId(itemId)))
						.withItems(items)
						.build());
	}

	@Override
	public Observable<PagingLink<PaginatedRecommendationsAccessoriesIdentifier>> getPagingLinks(final PaginatedRecommendationsAccessoriesIdentifier identifier) {
		int pageId = identifier.getPageId().getValue();
		return getRecommendedItemsFromGroup(identifier)
				.map(PaginatedResult::getNumberOfPages)
				.flatMapObservable(maxNumPages -> createPagingLinks(pageId, maxNumPages, identifier));
	}

	@Override
	protected PaginatedRecommendationsAccessoriesIdentifier buildPageIdentifier(final PaginatedRecommendationsAccessoriesIdentifier identifier, final IdentifierPart<Integer> pageId) {
		return PaginatedRecommendationsAccessoriesIdentifier
				.builderFrom(identifier)
				.withPageId(pageId)
				.build();
	}

	/**
	 * Get recommended items from group.
	 *
	 * @param paginatedRecommendationsAccessoriesIdentifier paginated recommendations identifier
	 * @return paginated result
	 */
	protected Single<PaginatedResult> getRecommendedItemsFromGroup(final PaginatedRecommendationsAccessoriesIdentifier paginatedRecommendationsAccessoriesIdentifier) {
		int pageId = paginatedRecommendationsAccessoriesIdentifier.getPageId().getValue();
		ItemRecommendationAccessoriesIdentifier identifier = paginatedRecommendationsAccessoriesIdentifier.getItemRecommendationAccessories();
		String groupId = "ACCESSORY";
		ItemIdentifier itemIdentifier = identifier.getItem();
		IdentifierPart<Map<String, String>> itemId = itemIdentifier.getItemId();
		String scope = itemIdentifier.getItems().getScope().getValue();

		return getPaginatedResult(scope, groupId, pageId, itemId);
	}

	/**
	 * Get paginated result for the recommendation group.
	 *
	 * @param scope scope
	 * @param recommendationGroup recommendation group id
	 * @param pageNumber page number
	 * @param itemId item id
	 * @return paginated result
	 */
	protected Single<PaginatedResult> getPaginatedResult(final String scope, final String recommendationGroup,
													   final int pageNumber, final IdentifierPart<Map<String, String>> itemId) {

		//Deep down item repository expects an encoded id
		String encodedItemId = identifierTransformerProvider.forUriPart(ItemIdentifier.ITEM_ID).identifierToUri(itemId);
		return itemRepository.getSkuForItemIdAsSingle(encodedItemId)
				.map(ProductSku::getProduct)
				.flatMap(product -> storeRepository.findStoreAsSingle(scope)
						.flatMap(store -> itemRecommendationsRepository
								.getRecommendedItemsFromGroup(store, product, recommendationGroup, pageNumber)));
	}

	/**
	 * Convert pagination DTO to pagination entity.
	 *
	 * @param paginatedResult paginated result
	 * @return pagination entity
	 */
	protected Single<PaginationEntity> convertPaginationResultToPaginationEntity(final PaginatedResult paginatedResult) {
		return Single.just(PaginationEntity.builder()
				.withCurrent(paginatedResult.getCurrentPage())
				.withPages(paginatedResult.getNumberOfPages())
				.withPageSize(paginatedResult.getResultsPerPage())
				.withResults(paginatedResult.getTotalNumberOfResults())
				.withResultsOnPage(paginatedResult.getResultIds().size())
				.build());
	}

	@Reference
	public void setItemRepository(final ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Reference
	public void setStoreRepository(final StoreRepository storeRepository) {
		this.storeRepository = storeRepository;
	}

	@Reference
	public void setItemRecommendationsRepository(final ItemRecommendationsRepository itemRecommendationsRepository) {
		this.itemRecommendationsRepository = itemRecommendationsRepository;
	}

	@Reference
	public void setIdentifierTransformerProvider(final IdentifierTransformerProvider identifierTransformerProvider) {
		this.identifierTransformerProvider = identifierTransformerProvider;
	}


}
