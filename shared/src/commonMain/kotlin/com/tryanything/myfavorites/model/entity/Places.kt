package com.tryanything.myfavorites.model.entity

import com.tryanything.myfavorites.model.dto.PlaceDto
import kotlinx.serialization.Serializable

@Serializable
internal data class Places(val places: List<Place>? = null)

@Serializable
internal data class Place(
    val id: String,
    val formattedAddress: String,
    val displayName: DisplayName,
    val location: Location,
    val photos: List<Photo>? = null
) {
    fun toDto(): PlaceDto = PlaceDto(
        id = id.hashCode().toLong(),
        name = displayName.text,
        address = formattedAddress,
        imageName = photos?.firstOrNull()?.name,
        latitude = location.latitude,
        longitude = location.longitude,
        isFavorite = false
    )
}

@Serializable
internal data class DisplayName(val text: String, val languageCode: String)

@Serializable
internal data class Location(val latitude: Double, val longitude: Double)

@Serializable
internal data class Photo(val name: String)
