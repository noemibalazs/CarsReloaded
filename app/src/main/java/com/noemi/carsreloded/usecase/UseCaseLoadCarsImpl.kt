package com.noemi.carsreloded.usecase

import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.repository.CarRepository

class UseCaseLoadCarsImpl(private val carRepository: CarRepository) : UseCaseLoadCars {

    override suspend operator fun invoke(): Result<List<Car>> = carRepository.loadRemoteCars()

}