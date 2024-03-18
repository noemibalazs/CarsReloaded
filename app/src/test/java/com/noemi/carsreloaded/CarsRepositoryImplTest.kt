package com.noemi.carsreloaded

import com.noemi.carsreloaded.helper.CarDispatcher
import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.model.network.NetworkResult
import com.noemi.carsreloaded.model.remote.RemoteCar
import com.noemi.carsreloaded.model.remote.toCar
import com.noemi.carsreloaded.remotedatasource.CarRemoteDataSource
import com.noemi.carsreloaded.repository.CarRepository
import com.noemi.carsreloaded.repository.CarRepositoryImpl
import com.noemi.carsreloaded.room.CarDAO
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CarsRepositoryImplTest {

    private val carDao: CarDAO = mockk()
    private val carRemoteDataSource: CarRemoteDataSource = mockk()

    private val carsDispatcher: CarDispatcher = mockk()
    private val remoteCar: RemoteCar = mockk()
    private val car: Car = mockk()

    private val cars = listOf(car)
    private val plateNumber = "TEST69"
    private val percentage = 69
    private val filterPlate = "TEST"
    private val percentageFilter = 81

    private lateinit var carRepository: CarRepository

    @Before
    fun setUp() {
        carRepository = CarRepositoryImpl(
            carDAO = carDao,
            carDispatcher = carsDispatcher,
            carRemoteDataSource = carRemoteDataSource
        )
    }

    @Test
    fun `test get cars and returns list of cars`() = runBlocking {

        coEvery { carsDispatcher.dispatcher() } returns Dispatchers.IO
        coEvery { carDao.getCars() } returns cars

        carRepository.getCars()

        coVerify { carDao.getCars() }
    }

    @Test
    fun `test get cars and returns empty list of cars`() = runBlocking {

        coEvery { carsDispatcher.dispatcher() } returns Dispatchers.IO
        coEvery { carDao.getCars() } returns emptyList()

        carRepository.getCars()

        coVerify { carDao.getCars() }
    }

    @Test
    fun `test filter cars by plate number and returns expected list`() = runBlocking {

        coEvery { carsDispatcher.dispatcher() } returns Dispatchers.IO
        coEvery { car.plateNumber } returns plateNumber
        coEvery { carDao.saveCars(cars) } just runs
        coEvery { carDao.filteredCarsByPlateNumber(filterPlate) } returns cars

        carRepository.saveCars(cars)
        carRepository.filteredByPlateNumber(filterPlate)

        coVerify { carDao.saveCars(cars) }
        coVerify { carDao.filteredCarsByPlateNumber(filterPlate) }
    }

    @Test
    fun `test filter cars by plate number and returns empty list`() = runBlocking {

        coEvery { carsDispatcher.dispatcher() } returns Dispatchers.IO
        coEvery { car.plateNumber } returns plateNumber
        coEvery { carDao.saveCars(cars) } just runs
        coEvery { carDao.filteredCarsByPlateNumber("") } returns emptyList()

        carRepository.saveCars(cars)
        carRepository.filteredByPlateNumber("")

        coVerify { carDao.saveCars(cars) }
        coVerify { carDao.filteredCarsByPlateNumber("") }
    }

    @Test
    fun `test filter cars by percentage and returns expected list`() = runBlocking {

        coEvery { carsDispatcher.dispatcher() } returns Dispatchers.IO
        coEvery { car.batteryPercentage } returns percentage
        coEvery { carDao.saveCars(cars) } just runs
        coEvery { carDao.filteredCarsByRemainingBattery(percentageFilter) } returns cars

        carRepository.saveCars(cars)
        carRepository.filteredByBatteryPercentage(percentageFilter)

        coVerify { carDao.saveCars(cars) }
        coVerify { carDao.filteredCarsByRemainingBattery(percentageFilter) }
    }

    @Test
    fun `test filter cars by percentage and returns empty list`() = runBlocking {

        coEvery { carsDispatcher.dispatcher() } returns Dispatchers.IO
        coEvery { car.batteryPercentage } returns percentage
        coEvery { carDao.saveCars(cars) } just runs
        coEvery { carDao.filteredCarsByRemainingBattery(90) } returns emptyList()

        carRepository.saveCars(cars)
        carRepository.filteredByBatteryPercentage(90)

        coVerify { carDao.saveCars(cars) }
        coVerify { carDao.filteredCarsByRemainingBattery(90) }
    }

    @Test
    fun `test save cars`() = runBlocking {

        coEvery { carsDispatcher.dispatcher() } returns Dispatchers.IO
        coEvery { carDao.saveCars(cars) } just runs

        carRepository.saveCars(cars)

        coVerify { carDao.saveCars(cars) }
    }

    @Test
    fun `test load remote cars and should be successful`() = runBlocking {

        mockkStatic(RemoteCar::toCar)
        coEvery { any<RemoteCar>().toCar() } returns car

        coEvery { carsDispatcher.dispatcher() } returns Dispatchers.IO
        coEvery { carRemoteDataSource.getRemoteCars() } returns NetworkResult.Success(listOf(remoteCar))

        carRepository.loadRemoteCars()

        coVerify { carRemoteDataSource.getRemoteCars() }
    }

    @Test
    fun `test load remote cars and should returns an error`() = runBlocking {

        coEvery { carsDispatcher.dispatcher() } returns Dispatchers.IO
        coEvery { carRemoteDataSource.getRemoteCars() } returns NetworkResult.Error("error")

        carRepository.loadRemoteCars()

        coVerify { carRemoteDataSource.getRemoteCars() }
    }

    @Test
    fun `test load remote cars and should be a failure`() = runBlocking {

        coEvery { carsDispatcher.dispatcher() } returns Dispatchers.IO
        coEvery { carRemoteDataSource.getRemoteCars() } returns NetworkResult.Failure(Throwable())

        carRepository.loadRemoteCars()

        coVerify { carRemoteDataSource.getRemoteCars() }
    }
}