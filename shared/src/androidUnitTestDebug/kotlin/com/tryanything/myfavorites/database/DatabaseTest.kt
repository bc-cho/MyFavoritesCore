package com.tryanything.myfavorites.database

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.tryanything.myfavorites.database.dao.FavoritePlaceDao
import com.tryanything.myfavorites.model.entity.FavoritePlaceEntity
import com.tryanything.myfavorites.test.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class DatabaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var database: AppDatabase
    private lateinit var favoriteDao: FavoritePlaceDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        favoriteDao = database.getFavoritePlaceDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun addFavorite() = runTest {
        favoriteDao.insert(FavoritePlaceEntity(0, "Place1", "Address1", null, 37.1, 127.1))
        favoriteDao.insert(FavoritePlaceEntity(0, "Place2", "Address2", null, 37.1, 127.1))
        assertEquals(favoriteDao.getAllFavorites().first().first().name, "Place1")
        assertEquals(favoriteDao.getAllFavorites().first().size, 2)
    }

    @Test
    fun deleteFavorite() = runTest {
        favoriteDao.insert(FavoritePlaceEntity(0, "Place1", "Address1", null, 37.1, 127.1))
        assertTrue(favoriteDao.getAllFavorites().first().isNotEmpty())
        favoriteDao.updateFavorite(
            favoriteDao.getAllFavorites().first().first().copy(deleted = 1)
        )
        assertTrue(favoriteDao.getAllFavorites().first().isEmpty())
    }
}
