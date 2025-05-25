package com.tryanything.myfavorites.model.dto

data class PlaceDto(
    val name: String,
    val address: String,
    val imageUrl: String? = null,
    val latitude: Double,
    val longitude: Double
)
