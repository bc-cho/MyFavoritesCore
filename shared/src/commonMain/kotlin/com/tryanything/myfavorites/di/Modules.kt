package com.tryanything.myfavorites.di

import com.tryanything.myfavorites.database.getFavoritePlaceDao
import com.tryanything.myfavorites.database.getRoomDatabase
import com.tryanything.myfavorites.network.DefaultPlacesService
import com.tryanything.myfavorites.network.PlaceService
import com.tryanything.myfavorites.network.getHttpClientEngine
import com.tryanything.myfavorites.repository.DefaultFavoriteRepository
import com.tryanything.myfavorites.repository.DefaultPlaceRepository
import com.tryanything.myfavorites.repository.FavoriteRepository
import com.tryanything.myfavorites.repository.PlaceRepository
import com.tryanything.myfavorites.repository.datasource.DatabaseDataSource
import com.tryanything.myfavorites.repository.datasource.DefaultDatabaseDataSource
import com.tryanything.myfavorites.repository.datasource.DefaultMemoryDataSource
import com.tryanything.myfavorites.repository.datasource.MemoryDataSource
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

expect fun databaseBuilderModule(): Module

expect fun mapHelperModule(): Module

val databaseModule = module {
    includes(databaseBuilderModule())
    singleOf(::getRoomDatabase)
    singleOf(::getFavoritePlaceDao)
    singleOf(::DefaultDatabaseDataSource) { bind<DatabaseDataSource>() }
}

val memoryDataModule = module {
    singleOf(::DefaultMemoryDataSource) { bind<MemoryDataSource>() }
}

val networkModule = module {
    includes(mapHelperModule())
    singleOf(::getHttpClientEngine)
    singleOf(::DefaultPlacesService) { bind<PlaceService>() }
}

val repositories = module {
    includes(databaseModule)
    includes(memoryDataModule)
    includes(networkModule)
    singleOf(::DefaultFavoriteRepository) { bind<FavoriteRepository>() }
    singleOf(::DefaultPlaceRepository) { bind<PlaceRepository>() }
}
