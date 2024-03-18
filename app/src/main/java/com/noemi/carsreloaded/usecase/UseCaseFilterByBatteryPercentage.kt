package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car

interface UseCaseFilterByBatteryPercentage {

    suspend operator fun invoke(battery: Int): List<Car>
}