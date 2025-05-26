package com.tryanything.myfavorites.di

import androidx.room.RoomDatabase
import com.tryanything.myfavorites.database.AppDatabase
import com.tryanything.myfavorites.database.getDatabaseBuilder
import com.tryanything.myfavorites.utils.MapsHelper
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun mapHelperModule(): Module = module {
    singleOf(::MapsHelper)
}

actual fun databaseBuilderModule(): Module = module {
    single<RoomDatabase.Builder<AppDatabase>> {
        getDatabaseBuilder()
    }
}
