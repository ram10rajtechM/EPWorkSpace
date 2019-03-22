package com.elasticpath.extensions.rest.resource.integration.epcommerce.repository.impl;

import com.elasticpath.domain.catalog.Product;
import com.elasticpath.domain.catalog.ProductSku;
import com.elasticpath.domain.store.Store;
import com.elasticpath.rest.definition.accessories.ItemRecommendationAccessoriesIdentifier;
import com.elasticpath.rest.definition.accessories.PaginatedRecommendationsAccessoriesIdentifier;
import com.elasticpath.rest.definition.items.ItemIdentifier;
import com.elasticpath.rest.definition.items.ItemsIdentifier;
import com.elasticpath.rest.id.Identifier;
import com.elasticpath.rest.id.IdentifierPart;
import com.elasticpath.rest.id.transform.IdentifierTransformer;
import com.elasticpath.rest.id.transform.IdentifierTransformerProvider;
import com.elasticpath.rest.id.type.CompositeIdentifier;
import com.elasticpath.rest.id.type.IntegerIdentifier;
import com.elasticpath.rest.id.type.StringIdentifier;
import com.elasticpath.rest.id.util.CompositeIdUtil;
import com.elasticpath.rest.pagination.PaginationEntity;
import com.elasticpath.rest.resource.integration.epcommerce.repository.item.ItemRepository;
import com.elasticpath.rest.resource.integration.epcommerce.repository.item.recommendations.ItemRecommendationsRepository;
import com.elasticpath.rest.resource.integration.epcommerce.repository.pagination.PaginatedResult;
import com.elasticpath.rest.resource.integration.epcommerce.repository.store.StoreRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import io.reactivex.Single;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.elasticpath.service.search.solr.SolrIndexConstants.SKU_CODE;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaginatedRecommendationsAccessoriesRepositoryImplTest {

    private static final String GROUP_ID = "ACCESSORY";
    /**
     * Test data for scope.
     */
    public static final String SCOPE = "scope";

    /**
     * Item id map, indexed with the established sku code key.
     */
    public static final Map<String, String> ITEM_ID_MAP = ImmutableMap.of(ItemRepository.SKU_CODE_KEY, SKU_CODE);
    /**
     * Test identifier part for item id.
     */
    public static final IdentifierPart<Map<String, String>> ITEM_IDENTIFIER_PART = CompositeIdentifier.of(ITEM_ID_MAP);

    private static final ImmutableList<String> RESULT_IDS = ImmutableList.of(encodeItemId("ID_1"), encodeItemId("ID_2"));

    private ItemsIdentifier items;
    @Mock
    private IdentifierTransformerProvider identifierTransformerProvider;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private ItemRecommendationsRepository itemRecommendationsRepository;

    @InjectMocks
    private PaginatedRecommendationsAccessoriesRepositoryImpl repository;
    @Mock
    private IdentifierTransformer<Identifier> transformer;
    @Mock
    private ProductSku productSku;
    @Mock
    private Product product;
    @Mock
    private Store store;
    @Mock
    private PaginatedResult paginatedResult;

    private static String encodeItemId(final String skuCode) {
        return CompositeIdUtil.encodeCompositeId(ImmutableSortedMap.of(ItemRepository.SKU_CODE_KEY, skuCode));
    }

    @Test
    public void  testGetRecommendedItemsFromGroup() {
        final int pageNumber = 1;
        PaginatedRecommendationsAccessoriesIdentifier identifier = mockPaginatedRecommendationsAccessoriesIdentifier(pageNumber);

        setUpRecommendationRepository(pageNumber);

        repository.getRecommendedItemsFromGroup(identifier)
                .test()
                .assertValue(paginatedResult);
    }

    @Test
    public void  testGetPagingLinksForTheFirstPage() {
        final int pageNumber = 1;
        PaginatedRecommendationsAccessoriesIdentifier identifier = mockPaginatedRecommendationsAccessoriesIdentifier(pageNumber);

        setUpRecommendationRepository(pageNumber);

        final int numPages = 10;
        when(paginatedResult.getNumberOfPages()).thenReturn(numPages);

        repository.getPagingLinks(identifier)
                .test()
                .assertValueCount(1);
    }

    @Test
    public void  testGetPagingLinksForTheMiddlePage() {
        final int pageNumber = 3;
        PaginatedRecommendationsAccessoriesIdentifier identifier = mockPaginatedRecommendationsAccessoriesIdentifier(pageNumber);

        setUpRecommendationRepository(pageNumber);

        final int numPages = 10;
        when(paginatedResult.getNumberOfPages()).thenReturn(numPages);


        repository.getPagingLinks(identifier)
                .test()
                .assertValueCount(2);
    }

    @Test
    public void  testGetElements() {
        final int pageNumber = 1;

        setUpRecommendationRepository(pageNumber);
        PaginatedRecommendationsAccessoriesIdentifier identifier = mockPaginatedRecommendationsAccessoriesIdentifier(pageNumber);

        when(paginatedResult.getResultIds()).thenReturn(RESULT_IDS);

        List<ItemIdentifier> expectedResult = RESULT_IDS.stream()
                .map(resultId -> ItemIdentifier.builder()
                        .withItemId(CompositeIdentifier.of(CompositeIdUtil.decodeCompositeId(resultId)))
                        .withItems(items)
                        .build())
                .collect(Collectors.toList());

        repository.getElements(identifier)
                .test()
                .assertValueSequence(expectedResult);
    }

    @Test
    public void  testGetPaginationInformation() {
        final int pageNumber = 1;

        PaginatedRecommendationsAccessoriesIdentifier identifier = mockPaginatedRecommendationsAccessoriesIdentifier(pageNumber);
        setUpRecommendationRepository(pageNumber);

        final int pageNum = 10;
        final int results = 1;

        when(paginatedResult.getCurrentPage()).thenReturn(pageNumber);
        when(paginatedResult.getNumberOfPages()).thenReturn(pageNum);
        when(paginatedResult.getResultsPerPage()).thenReturn(results);
        when(paginatedResult.getTotalNumberOfResults()).thenReturn(pageNum);
        when(paginatedResult.getResultIds()).thenReturn(RESULT_IDS);

        repository.getPaginationInfo(identifier)
                .test()
                .assertValue(PaginationEntity.builder()
                        .withCurrent(pageNumber)
                        .withPages(pageNum)
                        .withPageSize(results)
                        .withResults(pageNum)
                        .withResultsOnPage(RESULT_IDS.size())
                        .build()
                );
    }

    private void setUpRecommendationRepository(final int pageNumber) {
        when(identifierTransformerProvider.forUriPart(ItemIdentifier.ITEM_ID)).thenReturn(transformer);
        when(transformer.identifierToUri(ITEM_IDENTIFIER_PART)).thenReturn(SKU_CODE);
        when(itemRepository.getSkuForItemIdAsSingle(SKU_CODE)).thenReturn(Single.just(productSku));
        when(productSku.getProduct()).thenReturn(product);
        when(storeRepository.findStoreAsSingle(SCOPE)).thenReturn(Single.just(store));
        when(itemRecommendationsRepository.getRecommendedItemsFromGroup(store, product, GROUP_ID, pageNumber))
                .thenReturn(Single.just(paginatedResult));
    }

    private PaginatedRecommendationsAccessoriesIdentifier mockPaginatedRecommendationsAccessoriesIdentifier(
            final int pageNumber) {

        ItemIdentifier itemIdentifier = buildItemIdentifier(SCOPE, SKU_CODE);
        items = itemIdentifier.getItems();
        ItemRecommendationAccessoriesIdentifier itemRecommendationAccessoriesIdentifier = ItemRecommendationAccessoriesIdentifier.builder()
                .withItem(itemIdentifier)
                .build();
        return PaginatedRecommendationsAccessoriesIdentifier.builder()
                .withItemRecommendationAccessories(itemRecommendationAccessoriesIdentifier)
                .withPageId(IntegerIdentifier.of(pageNumber))
                .build();
    }

    /**
     * Builds an ItemsIdentifier with test data.
     *
     * @param scope the scope
     * @return ItemsIdentifier
     */
    public static ItemsIdentifier buildItemsIdentifier(final String scope) {
        return ItemsIdentifier.builder()
                .withScope(StringIdentifier.of(scope))
                .build();
    }

    /**
     * Builds an ItemIdentifier with test data.
     *
     * @param scope  the scope
     * @param skuCode the item sku code
     * @return ItemIdentifier
     */
    public static ItemIdentifier buildItemIdentifier(final String scope, final String skuCode) {
        return ItemIdentifier.builder()
                .withItemId(CompositeIdentifier.of(ImmutableMap.of(ItemRepository.SKU_CODE_KEY, skuCode)))
                .withItems(buildItemsIdentifier(scope))
                .build();
    }
}
