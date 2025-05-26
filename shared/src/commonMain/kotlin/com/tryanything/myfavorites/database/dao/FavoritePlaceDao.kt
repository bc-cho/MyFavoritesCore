package com.tryanything.myfavorites.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tryanything.myfavorites.model.entity.FavoritePlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePlaceDao {

    @Insert
    suspend fun insert(item: FavoritePlaceEntity)

    @Query("SELECT count(*) FROM FavoritePlaceEntity WHERE deleted = 0")
    suspend fun count(): Int

    @Query("SELECT * FROM FavoritePlaceEntity WHERE deleted = 0")
    fun getAllFavorites(): Flow<List<FavoritePlaceEntity>>

    @Query("SELECT * FROM FavoritePlaceEntity WHERE id IN (:ids) AND deleted = 0")
    suspend fun searchFavorite(ids: List<Long>): List<FavoritePlaceEntity>

    @Query("UPDATE FavoritePlaceEntity SET deleted = 1 WHERE id = :id")
    suspend fun deleteFavorite(id: Long): Int

    @Update
    suspend fun updateFavorite(id: FavoritePlaceEntity): Int
}
