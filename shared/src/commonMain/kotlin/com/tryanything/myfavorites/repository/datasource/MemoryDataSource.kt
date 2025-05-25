package com.tryanything.myfavorites.repository.datasource

import com.tryanything.myfavorites.model.dto.PlaceDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

typealias Keyword = String

internal interface MemoryDataSource {

    val searchResultData: StateFlow<Map<Keyword, List<PlaceDto>>>

    fun saveTextSearchResults(text: String, places: List<PlaceDto>)
}

internal class DefaultMemoryDataSource : MemoryDataSource {

    private val _searchResultData: MutableStateFlow<MutableMap<Keyword, List<PlaceDto>>> =
        MutableStateFlow(mutableMapOf<Keyword, List<PlaceDto>>())

    override val searchResultData: StateFlow<Map<Keyword, List<PlaceDto>>>
        get() = _searchResultData

    override fun saveTextSearchResults(text: String, places: List<PlaceDto>) {
        _searchResultData.update { results -> results.apply { put(text, places) } }
    }
}
