package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car

interface UseCaseFilterByPlateNumber {

    suspend operator fun invoke(plateNumber: String): List<Car>
}