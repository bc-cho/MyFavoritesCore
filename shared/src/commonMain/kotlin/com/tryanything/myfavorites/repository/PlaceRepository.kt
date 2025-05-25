package com.tryanything.myfavorites.repository

import com.tryanything.myfavorites.model.dto.PlaceDto
import com.tryanything.myfavorites.network.PlaceService
import com.tryanything.myfavorites.repository.datasource.MemoryDataSource

interface PlaceRepository {

    suspend fun searchByText(text: String): List<PlaceDto>
}

internal class DefaultPlaceRepository(
    private val memoryDataSource: MemoryDataSource,
    private val placeService: PlaceService
) : PlaceRepository {
    override suspend fun searchByText(text: String): List<PlaceDto> {
        return memoryDataSource.searchResultData.value[text] ?: placeService.searchByText(text)
            .also { searchResults -> memoryDataSource.saveTextSearchResults(text, searchResults) }
    }
}
