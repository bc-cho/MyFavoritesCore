package com.tryanything.myfavorites.repository.datasource

import com.tryanything.myfavorites.database.dao.FavoritePlaceDao
import com.tryanything.myfavorites.model.entity.FavoritePlaceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

internal interface DatabaseDataSource {

    suspend fun addFavoritePlace(item: FavoritePlaceEntity)

    suspend fun getAllFavoritePlace(): List<FavoritePlaceEntity>

    suspend fun searchFavoritePlace(ids: List<Long>): List<FavoritePlaceEntity>

    fun observeAllFavoritePlace(): Flow<List<FavoritePlaceEntity>>

    suspend fun deleteFavoritePlace(item: FavoritePlaceEntity)
}

internal class DefaultDatabaseDataSource(
    val favoritePlaceDao: FavoritePlaceDao
) : DatabaseDataSource {
    override suspend fun addFavoritePlace(item: FavoritePlaceEntity) {
        favoritePlaceDao.insert(item)
    }

    override suspend fun getAllFavoritePlace(): List<FavoritePlaceEntity> {
        return favoritePlaceDao.getAllFavorites().firstOrNull() ?: emptyList()
    }

    override suspend fun searchFavoritePlace(ids: List<Long>): List<FavoritePlaceEntity> {
        return favoritePlaceDao.searchFavorite(ids)
    }

    override fun observeAllFavoritePlace(): Flow<List<FavoritePlaceEntity>> {
        return favoritePlaceDao.getAllFavorites()
    }

    override suspend fun deleteFavoritePlace(item: FavoritePlaceEntity) {
        favoritePlaceDao.deleteFavorite(item.id)
    }
}
