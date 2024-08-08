package com.noemi.carsreloaded.repository

import com.noemi.carsreloaded.helper.CarDispatcher
import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.model.network.NetworkResult
import com.noemi.carsreloaded.model.remote.toCar
import com.noemi.carsreloaded.remotedatasource.CarRemoteDataSource
import com.noemi.carsreloaded.room.CarDAO
import kotlinx.coroutines.withContext

class CarRepositoryImpl(
    private val carDAO: CarDAO,
    private val carRemoteDataSource: CarRemoteDataSource,
    private val carDispatcher: CarDispatcher
) : CarRepository {

    override suspend fun saveCars(cars: List<Car>) = carDAO.saveCars(cars)

    override suspend fun loadRemoteCars(): Result<List<Car>> = withContext(carDispatcher.dispatcher()) {
        when (val cars = carRemoteDataSource.getRemoteCars()) {
            is NetworkResult.Success -> Result.success(cars.data.map { remoteCar -> remoteCar.toCar() })
            is NetworkResult.Error -> Result.failure(Throwable(cars.message))
            is NetworkResult.Failure -> Result.failure(cars.error)
        }
    }

    override suspend fun filteredByPlateNumber(plate: String): List<Car> = carDAO.filteredCarsByPlateNumber(plate)

    override suspend fun filteredByBatteryPercentage(battery: Int): List<Car> = carDAO.filteredCarsByRemainingBattery(battery)

    override suspend fun getCars(): List<Car> = carDAO.getCars()
}