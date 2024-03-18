package com.noemi.carsreloded.usecase

import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.repository.CarRepository

class UseCaseGetCarsImpl(private val carRepository: CarRepository): UseCaseGetCars{

   override suspend operator fun invoke(): List<Car> = carRepository.getCars()
}