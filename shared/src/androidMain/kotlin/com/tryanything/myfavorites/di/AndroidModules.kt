package com.tryanything.myfavorites.di

import com.tryanything.myfavorites.network.DefaultPlacesService
import com.tryanything.myfavorites.network.PlaceService
import com.tryanything.myfavorites.utils.MapsHelper
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val services = module {
    singleOf(::MapsHelper)
    factoryOf(::DefaultPlacesService) { bind<PlaceService>() }
}
