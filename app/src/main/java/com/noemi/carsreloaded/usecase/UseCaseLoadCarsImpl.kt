package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository

class UseCaseLoadCarsImpl(private val carRepository: CarRepository) : UseCaseLoadCars {

    override suspend operator fun invoke(): Result<List<Car>> = carRepository.loadRemoteCars()

}