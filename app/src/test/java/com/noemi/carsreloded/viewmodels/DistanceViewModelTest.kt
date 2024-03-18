package com.noemi.carsreloded.viewmodels

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.noemi.carsreloded.helper.DataManager
import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.model.local.CarLocation
import com.noemi.carsreloded.screens.distance.DistanceViewModel
import com.noemi.carsreloded.usecase.UseCaseGetCars
import com.noemi.carsreloded.util.meterToKm
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class DistanceViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()

    private val useCaseGetCars: UseCaseGetCars = mockk()
    private val dataManager: DataManager = mockk()
    private val carLocation: CarLocation = mockk()
    private val userLocation:Location = mockk()

    private val lastLatitude = 23.44
    private val lastLongitude = 36.78

    private val latitude = 34.22
    private val longitude = 67.34
    private val result = 0.03

    private val carsObserver: Observer<List<Car>> = mockk()
    private val carsCaptor = mutableListOf<List<Car>>()

    private val car: Car = mockk()
    private val cars = listOf(car)

    private lateinit var viewModel: DistanceViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = DistanceViewModel(
            dataManager = dataManager,
            useCaseGetCars = useCaseGetCars
        )

        carsCaptor.clear()
        coEvery { carsObserver.onChanged(capture(carsCaptor)) } just runs
        viewModel.cars.observeForever(carsObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test load cars`() = runBlocking {
        val sorted: SortedMap<Double, Car> = sortedMapOf()
        sorted[result] = car

        coEvery { dataManager.setLastKnownLatitude(lastLatitude) } just runs
        coEvery { dataManager.setLastKnowLongitude(lastLongitude) } just runs

        coEvery { dataManager.getLastKnownLatitude() } returns lastLatitude
        coEvery { dataManager.getLastKnownLongitude() } returns lastLongitude

        coEvery { car.location } returns carLocation
        coEvery { carLocation.longitude } returns longitude
        coEvery { carLocation.latitude } returns latitude

        mockkStatic(Float::meterToKm)
        coEvery { any<Float>().meterToKm() } returns result

        coEvery { useCaseGetCars.invoke() } returns cars

        viewModel.loadCars()

        coVerify { dataManager.getLastKnownLongitude() }
        coVerify { dataManager.getLastKnownLatitude() }
        coVerify { useCaseGetCars.invoke() }
        coVerify { carsObserver.onChanged(sorted.values.toList()) }
    }

    @Test
    fun `test save user location`() = runBlocking {
        coEvery { userLocation.latitude } returns latitude
        coEvery { userLocation.longitude } returns longitude

        coEvery { dataManager.setLastKnowLongitude(longitude) } just runs
        coEvery { dataManager.setLastKnownLatitude(latitude) } just runs

        viewModel.saveUserLocationCoordinates(userLocation)

        coVerify { dataManager.setLastKnownLatitude(latitude) }
        coVerify { dataManager.setLastKnowLongitude(longitude) }
    }

}