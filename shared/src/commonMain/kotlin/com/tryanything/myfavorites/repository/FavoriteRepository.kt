package com.tryanything.myfavorites.repository

import com.tryanything.myfavorites.model.dto.FavoriteDto
import com.tryanything.myfavorites.model.entity.FavoritePlaceEntity
import com.tryanything.myfavorites.repository.datasource.DatabaseDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface FavoriteRepository {

    suspend fun addFavorite(item: FavoriteDto)

    suspend fun getAllFavorites(): List<FavoriteDto>

    suspend fun deleteFavorite(item: FavoriteDto)

    fun observeAllFavorites(): Flow<List<FavoriteDto>>
}

internal class DefaultFavoriteRepository(
    val databaseDataSource: DatabaseDataSource
) : FavoriteRepository {
    override suspend fun addFavorite(item: FavoriteDto) {
        databaseDataSource.addFavoritePlace(FavoritePlaceEntity(item))
    }

    override suspend fun getAllFavorites(): List<FavoriteDto> {
        return databaseDataSource.getAllFavoritePlace().map { FavoriteDto(it) }
    }

    override suspend fun deleteFavorite(item: FavoriteDto) {
        databaseDataSource.deleteFavoritePlace(FavoritePlaceEntity(item))
    }

    override fun observeAllFavorites(): Flow<List<FavoriteDto>> {
        return databaseDataSource.observeAllFavoritePlace()
            .map { entities -> entities.map { FavoriteDto(it) } }
    }
}
