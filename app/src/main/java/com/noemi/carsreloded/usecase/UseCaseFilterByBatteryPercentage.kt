package com.noemi.carsreloded.usecase

import com.noemi.carsreloded.model.local.Car

interface UseCaseFilterByBatteryPercentage {

    suspend operator fun invoke(battery: Int): List<Car>
}