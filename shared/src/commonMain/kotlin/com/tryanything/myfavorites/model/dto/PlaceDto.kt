package com.tryanything.myfavorites.model.dto

data class PlaceDto(
    val id: Long,
    val name: String,
    val address: String,
    val photoName: String? = null,
    val latitude: Double,
    val longitude: Double,
    val isFavorite: Boolean = false
)
