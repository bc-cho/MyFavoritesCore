package com.tryanything.myfavorites.model.dto

data class PlaceDto(
    val id: Long,
    val name: String,
    val address: String,
    val imageName: String? = null,
    val imageUrl: String? = null,
    val latitude: Double,
    val longitude: Double,
    val isFavorite: Boolean = false
)
