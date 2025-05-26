package com.tryanything.myfavorites.repository

import com.tryanything.myfavorites.model.dto.PlaceDto
import com.tryanything.myfavorites.network.PlaceService
import com.tryanything.myfavorites.repository.datasource.DefaultMemoryDataSource
import com.tryanything.myfavorites.repository.datasource.MemoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class PlaceRepositoryTest {

    private lateinit var placeRepository: PlaceRepository

    private lateinit var memoryDataSource: MemoryDataSource

    private lateinit var mockPlaceService: MockPlayService

    @BeforeTest
    fun setUp() {
        mockPlaceService = MockPlayService()
        memoryDataSource = DefaultMemoryDataSource()
        placeRepository = DefaultPlaceRepository(memoryDataSource, mockPlaceService)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testIfThereIsNoCacheBeforeSearch() {
        assertTrue(memoryDataSource.searchResultData.value.isEmpty())
    }

    @Test
    fun testIfThereIsCacheAfterSearch() = runTest {
        mockPlaceService.setResults(twoPlaces)
        placeRepository.searchByText("station")
        assertTrue(memoryDataSource.searchResultData.value.isNotEmpty())
    }

    @Test
    fun testNoAPICallWhenCacheExists() = runTest {
        mockPlaceService.setResults(twoPlaces)
        val networkResult = placeRepository.searchByText("station")

        assertTrue(networkResult.isNotEmpty())

        mockPlaceService.setResults(noResults)
        val result = placeRepository.searchByText("station")

        assertTrue(memoryDataSource.searchResultData.value["station"]?.size == 2)
        assertTrue(result.isNotEmpty())
    }

    private val twoPlaces = listOf(
        PlaceDto(
            id = 0L,
            name = "Harajuku Station",
            address = "〒150-0001 東京都渋谷区神宮前１丁目１８",
            latitude = 0.0,
            longitude = 0.0

        ),
        PlaceDto(
            id = 1L,
            name = "Shinjuku Station",
            address = "〒160-0022 東京都新宿区新宿３丁目３８−１",
            latitude = 0.0,
            longitude = 0.0
        )
    )

    private val noResults = listOf<PlaceDto>()

    // FIXME: Mockativeを使う
    private class MockPlayService : PlaceService {
        private var _results: MutableList<PlaceDto> = mutableListOf()

        fun setResults(newValue: List<PlaceDto>) {
            _results.clear()
            _results.addAll(newValue)
        }

        override suspend fun searchByText(text: String): List<PlaceDto> {
            return if (text == "station") _results.toList() else emptyList()
        }
    }
}
