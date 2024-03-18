package com.noemi.carsreloded.usecase

import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.repository.CarRepository

class UseCaseFilterByPlateNumberImpl(private val carRepository: CarRepository) : UseCaseFilterByPlateNumber {

    override suspend operator fun invoke(plateNumber: String): List<Car> = carRepository.filteredByPlateNumber(plateNumber)
}