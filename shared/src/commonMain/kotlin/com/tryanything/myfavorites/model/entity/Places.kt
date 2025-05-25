package com.tryanything.myfavorites.model.entity

import com.tryanything.myfavorites.model.dto.PlaceDto
import kotlinx.serialization.Serializable

@Serializable
internal data class Places(val places: List<Place>?)

@Serializable
internal data class Place(
    val formattedAddress: String,
    val displayName: DisplayName
) {
    fun toDto(): PlaceDto = PlaceDto(name = displayName.text, address = formattedAddress)
}

@Serializable
internal data class DisplayName(val text: String, val languageCode: String)
