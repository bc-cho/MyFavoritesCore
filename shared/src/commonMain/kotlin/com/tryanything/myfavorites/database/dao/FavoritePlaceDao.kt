package com.tryanything.myfavorites.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tryanything.myfavorites.model.entity.FavoritePlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePlaceDao {

    @Insert
    suspend fun insert(item: FavoritePlaceEntity)

    @Query("SELECT count(*) FROM FavoritePlaceEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM FavoritePlaceEntity")
    fun getAllFavorites(): Flow<List<FavoritePlaceEntity>>
}
