package com.tryanything.myfavorites.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tryanything.myfavorites.model.dto.FavoriteDto

@Entity
data class FavoritePlaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val address: String,
    val imageName: String? = null,
    val latitude: Double,
    val longitude: Double,
    val deleted: Int = 0
) {
    constructor(item: FavoriteDto) : this(
        id = item.id,
        name = item.name,
        address = item.address,
        imageName = item.imageName,
        latitude = item.lat,
        longitude = item.lon
    )
}
