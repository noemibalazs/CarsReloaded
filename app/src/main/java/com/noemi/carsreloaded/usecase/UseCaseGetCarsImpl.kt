package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository

class UseCaseGetCarsImpl(private val carRepository: CarRepository): UseCaseGetCars{

   override suspend operator fun invoke(): List<Car> = carRepository.getCars()
}