package com.tryanything.myfavorites.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tryanything.myfavorites.model.dto.FavoriteDto

@Entity
data class FavoritePlaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val address: String,
    val imageUrl: String? = null,
    val latitude: Double,
    val longitude: Double,
    val deleted: Boolean = false
) {
    constructor(item: FavoriteDto) : this(
        name = item.name,
        address = item.address,
        imageUrl = null,
        latitude = item.lat,
        longitude = item.lon
    )
}
