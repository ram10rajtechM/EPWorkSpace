package com.elasticpath.extensions.rest.resource.accessories.prototype;

import com.elasticpath.extensions.rest.resource.prototype.ReadPaginatedRecommendationsAccessoriesPrototype;
import com.elasticpath.repository.PaginationRepository;
import com.elasticpath.rest.definition.accessories.PaginatedRecommendationsAccessoriesIdentifier;
import com.elasticpath.rest.pagination.PaginationEntity;
import io.reactivex.Single;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReadPaginatedRecommendationsAccessoriesPrototypeTest {

    @InjectMocks
    ReadPaginatedRecommendationsAccessoriesPrototype readPaginatedRecommendationsAccessoriesPrototype;

    @Mock
    PaginationRepository paginationRepository;

    @Mock
    PaginatedRecommendationsAccessoriesIdentifier paginatedRecommendationsAccessoriesIdentifier;

    @Test
    public void shouldReturnPaginatedRecommendationsAccessories() {
        PaginationEntity paginationEntity = PaginationEntity.builder().withCurrent(1).withPages(1).withPageSize(3).withResults(3).withResultsOnPage(5).build();
        when(paginationRepository.getPaginationInfo(paginatedRecommendationsAccessoriesIdentifier)).thenReturn(Single.just(paginationEntity));
        Single<PaginationEntity> result = readPaginatedRecommendationsAccessoriesPrototype.onRead();
        Assertions.assertThat(result.blockingGet()).hasFieldOrPropertyWithValue("current", 1);
       /* verify(paginationRepository).getElements(paginatedRecommendationsAccessoriesIdentifier);
        verify(paginationRepository).getPagingLinks(paginatedRecommendationsAccessoriesIdentifier);*/

    }

}
