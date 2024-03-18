package com.noemi.carsreloded.repository

import com.noemi.carsreloded.model.local.Car

interface CarRepository {

    suspend fun saveCars(cars: List<Car>)

    suspend fun loadRemoteCars(): Result<List<Car>>

    suspend fun filteredByPlateNumber(plate: String): List<Car>

    suspend fun filteredByBatteryPercentage(battery: Int): List<Car>

    suspend fun getCars(): List<Car>
}