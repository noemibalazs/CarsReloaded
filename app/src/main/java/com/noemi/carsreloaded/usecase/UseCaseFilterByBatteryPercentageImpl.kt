package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository

class UseCaseFilterByBatteryPercentageImpl(private val carRepository: CarRepository) : UseCaseFilterByBatteryPercentage {

    override suspend operator fun invoke(battery: Int): List<Car> = carRepository.filteredByBatteryPercentage(battery)
}