package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository

class LoadCarsUseCase(private val carRepository: CarRepository) {

    suspend operator fun invoke(): Result<List<Car>> = carRepository.loadRemoteCars()
}