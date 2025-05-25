package com.tryanything.myfavorites.model.dto

import com.tryanything.myfavorites.model.entity.FavoritePlaceEntity

class FavoriteDto(
    val id: Long,
    val name: String,
    val address: String,
    val imageUrl: String? = null,
    val lat: Double,
    val lon: Double
) {
    constructor(item: FavoritePlaceEntity) : this(
        id = item.id,
        name = item.name,
        address = item.address,
        imageUrl = item.imageUrl,
        lat = item.latitude,
        lon = item.longitude
    )
}
