package com.tryanything.myfavorites.network

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.tryanything.myfavorites.database.AppDatabase
import com.tryanything.myfavorites.database.dao.FavoritePlaceDao
import com.tryanything.myfavorites.model.entity.FavoritePlaceEntity
import com.tryanything.myfavorites.repository.datasource.DatabaseDataSource
import com.tryanything.myfavorites.repository.datasource.DefaultDatabaseDataSource
import com.tryanything.myfavorites.test.MainDispatcherRule
import com.tryanything.myfavorites.utils.MapsHelper
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class PlaceServiceTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var database: AppDatabase
    private lateinit var favoriteDao: FavoritePlaceDao

    private lateinit var placeService: PlaceService

    private lateinit var databaseDataSource: DatabaseDataSource

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        favoriteDao = database.getFavoritePlaceDao()

        databaseDataSource = DefaultDatabaseDataSource(favoriteDao)

        placeService = DefaultPlacesService(mockEngine, MapsHelper(), databaseDataSource)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun setFavoriteWhenFindFavoriteDb() = runTest {
        favoriteDao.insert(
            FavoritePlaceEntity(
                id = "ChIJs5ydyTiuEmsR0fRSlU0C7k0".hashCode().toLong(),
                name = "Peace Harmony",
                address = "29 King St, Sydney NSW 2000, Australia",
                latitude = -33.8688699,
                longitude = 151.20402529999998
            )
        )
        val results = placeService.searchByText("searchText")
        assertTrue(results.size == 3)

        results.forEach { item ->
            assertTrue(item.isFavorite == (item.name == "Peace Harmony"))
        }
    }

    val mockEngine = MockEngine { request ->
        respond(
            content = mockResponse,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    private val mockResponse =
        "{\n" +
                "  \"places\": [\n" +
                "    {\n" +
                "      \"id\": \"ChIJs5ydyTiuEmsR0fRSlU0C7k0\",\n" +
                "      \"formattedAddress\": \"29 King St, Sydney NSW 2000, Australia\",\n" +
                "      \"location\": {\n" +
                "        \"latitude\": -33.8688699,\n" +
                "        \"longitude\": 151.20402529999998\n" +
                "      },\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Peace Harmony\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ChIJwWB_qT2uEmsRbE92vXMyX4Q\",\n" +
                "      \"formattedAddress\": \"367 Pitt St, Sydney NSW 2000, Australia\",\n" +
                "      \"location\": {\n" +
                "        \"latitude\": -33.875924999999995,\n" +
                "        \"longitude\": 151.20777099999998\n" +
                "      },\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Mother Chu's Vegetarian Kitchen\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ChIJUwJPSHOxEmsRlxW9EbU2cpg\",\n" +
                "      \"formattedAddress\": \"101 Cleveland St, Darlington NSW 2008, Australia\",\n" +
                "      \"location\": {\n" +
                "        \"latitude\": -33.8883498,\n" +
                "        \"longitude\": 151.1962467\n" +
                "      },\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Tian Ci Vegan\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}"
}
