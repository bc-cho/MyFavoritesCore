package com.tryanything.myfavorites.di

import com.tryanything.myfavorites.network.DefaultPlacesService
import com.tryanything.myfavorites.network.PlaceService
import com.tryanything.myfavorites.repository.DefaultPlaceRepository
import com.tryanything.myfavorites.repository.PlaceRepository
import com.tryanything.myfavorites.repository.datasource.MemoryDataSource
import com.tryanything.myfavorites.repository.datasource.DefaultMemoryDataSource
import com.tryanything.myfavorites.utils.MapsHelper
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositories = module {
    singleOf(::MapsHelper)
    singleOf(::DefaultMemoryDataSource) { bind<MemoryDataSource>() }
    singleOf(::DefaultPlacesService) { bind<PlaceService>() }
    singleOf(::DefaultPlaceRepository) { bind<PlaceRepository>() }
}
