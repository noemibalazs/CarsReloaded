package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository

class UseCaseSaveCarsImpl(private val carRepository: CarRepository) : UseCaseSaveCars {

    override suspend fun invoke(cars: List<Car>) = carRepository.saveCars(cars)
}