package com.tryanything.myfavorites.network

import com.tryanything.myfavorites.model.dto.PlaceDto
import com.tryanything.myfavorites.model.entity.Places
import com.tryanything.myfavorites.utils.MapsHelper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.mockative.Mockable
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private const val BASE_HOST = "places.googleapis.com"

// TODO: all-openの使用を検討
@Mockable
interface PlaceService {
    suspend fun searchByText(text: String): List<PlaceDto>
}

internal class DefaultPlacesService(val mapsHelper: MapsHelper) : PlaceService {

    private val httpClient = HttpClient {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_HOST
                parameters["key"] = mapsHelper.getApiKey()
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
            header("X-Goog-FieldMask", "places.displayName,places.formattedAddress")
            setBody(Json.encodeToString(SearchRequest(text)))
        }.body<Places>()
        return result.places?.map { it.toDto() } ?: emptyList()
    }

    @Serializable
    private data class SearchRequest(val textQuery: String)
}
