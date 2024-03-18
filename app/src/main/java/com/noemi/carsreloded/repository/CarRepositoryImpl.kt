package com.noemi.carsreloded.repository

import com.noemi.carsreloded.helper.CarDispatcher
import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.model.network.NetworkResult
import com.noemi.carsreloded.model.remote.toCar
import com.noemi.carsreloded.model.remotedatasource.CarRemoteDataSource
import com.noemi.carsreloded.room.CarDAO
import kotlinx.coroutines.withContext

class CarRepositoryImpl(
    private val carDAO: CarDAO,
    private val carRemoteDataSource: CarRemoteDataSource,
    private val carDispatcher: CarDispatcher
) : CarRepository {

    override suspend fun saveCars(cars: List<Car>) = withContext(carDispatcher.dispatcher()) {
        carDAO.saveCars(cars)
    }

    override suspend fun loadRemoteCars(): Result<List<Car>> = withContext(carDispatcher.dispatcher()) {
        when (val cars = carRemoteDataSource.getRemoteCars()) {
            is NetworkResult.Success -> Result.success(cars.data.map { remoteCar -> remoteCar.toCar() })
            is NetworkResult.Error -> Result.failure(Throwable(cars.message))
            is NetworkResult.Failure -> Result.failure(cars.error)
        }
    }

    override suspend fun filteredByPlateNumber(plate: String): List<Car> = withContext(carDispatcher.dispatcher()) {
        carDAO.filteredCarsByPlateNumber(plate)
    }

    override suspend fun filteredByBatteryPercentage(battery: Int): List<Car> = withContext(carDispatcher.dispatcher()) {
        carDAO.filteredCarsByRemainingBattery(battery)
    }

    override suspend fun getCars(): List<Car> = withContext(carDispatcher.dispatcher()) {
        carDAO.getCars()
    }
}