package com.noemi.carsreloded.usecase

import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.repository.CarRepository

class UseCaseSaveCarsImpl(private val carRepository: CarRepository) : UseCaseSaveCars {

    override suspend fun invoke(cars: List<Car>) = carRepository.saveCars(cars)
}