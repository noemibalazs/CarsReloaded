package com.noemi.carsreloded.usecase

import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.repository.CarRepository

class UseCaseFilterByBatteryPercentageImpl(private val carRepository: CarRepository) : UseCaseFilterByBatteryPercentage {

    override suspend operator fun invoke(battery: Int): List<Car> = carRepository.filteredByBatteryPercentage(battery)
}