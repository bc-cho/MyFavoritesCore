package com.tryanything.myfavorites.repository

import com.tryanything.myfavorites.model.dto.PlaceDto
import com.tryanything.myfavorites.network.PlaceService
import com.tryanything.myfavorites.repository.datasource.DefaultMemoryDataSource
import com.tryanything.myfavorites.repository.datasource.MemoryDataSource
import io.mockative.coEvery
import io.mockative.coVerify
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

internal class PlaceRepositoryTest {

    lateinit var placeRepository: PlaceRepository

    lateinit var memoryDataSource: MemoryDataSource

    lateinit var placeService: PlaceService

    @BeforeTest
    fun test() {
        placeService = mock(of<PlaceService>())
        memoryDataSource = DefaultMemoryDataSource()
        placeRepository = DefaultPlaceRepository(memoryDataSource, placeService)
    }

    @Test
    fun testIfThereIsNoCacheBeforeSearch() {
        assertTrue(memoryDataSource.searchResultData.value.isEmpty())
    }

    @Test
    fun testIfThereIsCacheAfterSearch() = runTest {
        coEvery {
            placeService.searchByText("station")
        }.returns(places1)
        placeRepository.searchByText("station")
        assertTrue(memoryDataSource.searchResultData.value.isNotEmpty())
    }

    @Test
    fun testNoAPICallWhenCacheExists() = runTest {
        coEvery {
            placeService.searchByText("station")
        }.returns(places1)
        placeRepository.searchByText("station")
        coEvery {
            placeService.searchByText("station")
        }.returns(emptyList())
        val result = placeRepository.searchByText("station")
        assertTrue(memoryDataSource.searchResultData.value.isNotEmpty())
        assertTrue(result.isNotEmpty())
        coVerify {placeService.searchByText("station") }.wasInvoked(exactly = 1)
    }

    private val places1 = listOf(
        PlaceDto(
            name = "Harajuku Station",
            address = "〒150-0001 東京都渋谷区神宮前１丁目１８"
        ),
        PlaceDto(
            name = "Shinjuku Station",
            address = "〒160-0022 東京都新宿区新宿３丁目３８−１"
        )
    )
}
