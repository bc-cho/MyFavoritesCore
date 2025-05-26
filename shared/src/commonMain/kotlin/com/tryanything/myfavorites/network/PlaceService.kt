package com.tryanything.myfavorites.network

import com.tryanything.myfavorites.model.dto.PlaceDto
import com.tryanything.myfavorites.model.entity.FavoritePlaceEntity
import com.tryanything.myfavorites.model.entity.Places
import com.tryanything.myfavorites.repository.datasource.DatabaseDataSource
import com.tryanything.myfavorites.utils.MapsHelper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private const val BASE_HOST = "places.googleapis.com"

interface PlaceService {
    suspend fun searchByText(text: String): List<PlaceDto>
}

internal class DefaultPlacesService(
    engine: HttpClientEngine,
    val mapsHelper: MapsHelper,
    val databaseDataSource: DatabaseDataSource
) : PlaceService {

    private val httpClient = HttpClient(engine) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_HOST
            }
        }
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    override suspend fun searchByText(text: String): List<PlaceDto> {
        val result = httpClient.post("v1/places:searchText") {
            contentType(ContentType.Application.Json)
            header("X-Goog-Api-Key", mapsHelper.getApiKey())
            header(
                "X-Goog-FieldMask",
                "places.id,places.displayName,places.formattedAddress,places.location,places.photos"
            )
            setBody(Json.encodeToString(SearchRequest(text)))
        }.body<Places>()
        val results = result.places?.map { entity -> entity.toDto() } ?: emptyList()
        if (results.isEmpty()) {
            return results
        }
        val favoritePlaces = databaseDataSource.searchFavoritePlace(results.map { it.id })
        return results.map { dto -> dto.copy(isFavorite = dto.checkIfFavorite(favoritePlaces)) }
    }

    private fun PlaceDto.checkIfFavorite(favorites: List<FavoritePlaceEntity>): Boolean {
        return favorites.any { favorite -> favorite.id == id }
    }

    @Serializable
    private data class SearchRequest(val textQuery: String)
}
