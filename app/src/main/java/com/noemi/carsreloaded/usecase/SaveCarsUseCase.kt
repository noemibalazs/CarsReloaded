package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository

class SaveCarsUseCase(private val carRepository: CarRepository) {

    suspend fun invoke(cars: List<Car>) = carRepository.saveCars(cars)
}