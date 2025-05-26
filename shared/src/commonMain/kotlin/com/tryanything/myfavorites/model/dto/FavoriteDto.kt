package com.tryanything.myfavorites.model.dto

import com.tryanything.myfavorites.model.entity.FavoritePlaceEntity

data class FavoriteDto(
    val id: Long = 0L,
    val name: String,
    val address: String,
    val imageName: String? = null,
    val imageUrl: String? = null,
    val lat: Double,
    val lon: Double
) {
    constructor(item: FavoritePlaceEntity) : this(
        id = item.id,
        name = item.name,
        address = item.address,
        lat = item.latitude,
        lon = item.longitude
    )
}
