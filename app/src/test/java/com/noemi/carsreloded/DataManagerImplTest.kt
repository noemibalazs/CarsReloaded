package com.noemi.carsreloded

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.noemi.carsreloded.helper.DataManager
import com.noemi.carsreloded.helper.DataManagerImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataManagerImplTest {

    private lateinit var dataManager: DataManager

    private val latitude = 45.33
    private val longitude = 49.12

    @Before
    fun setUp() {
        dataManager = DataManagerImpl(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun `test set last known longitude`() = runBlocking {
        dataManager.setLastKnowLongitude(longitude)
        val result = dataManager.getLastKnownLongitude()
        assertEquals(result, longitude)
    }

    @Test
    fun `test set last known latitude`() = runBlocking {
        dataManager.setLastKnownLatitude(latitude)
        val result = dataManager.getLastKnownLatitude()
        assertEquals(result, latitude)
    }
}