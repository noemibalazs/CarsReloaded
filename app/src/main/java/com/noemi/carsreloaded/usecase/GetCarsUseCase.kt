package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository

class GetCarsUseCase(private val carRepository: CarRepository) {

    suspend operator fun invoke(): List<Car> = carRepository.getCars()
}