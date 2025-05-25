package com.tryanything.myfavorites.model.entity

import com.tryanything.myfavorites.model.dto.PlaceDto
import kotlinx.serialization.Serializable

@Serializable
internal data class Places(val places: List<Place>? = null)

@Serializable
internal data class Place(
    val formattedAddress: String,
    val displayName: DisplayName,
    val location: Location,
    val photos: List<Photo>? = null
) {
    fun toDto(): PlaceDto = PlaceDto(
        name = displayName.text,
        address = formattedAddress,
        imageUrl = photos?.firstOrNull()?.name,
        latitude = location.latitude,
        longitude = location.longitude,
    )
}

@Serializable
internal data class DisplayName(val text: String, val languageCode: String)

@Serializable
internal data class Location(val latitude: Double, val longitude: Double)

@Serializable
internal data class Photo(val name: String)
