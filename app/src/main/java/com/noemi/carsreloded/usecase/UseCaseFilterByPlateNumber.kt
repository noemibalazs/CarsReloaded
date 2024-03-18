package com.noemi.carsreloded.usecase

import com.noemi.carsreloded.model.local.Car

interface UseCaseFilterByPlateNumber {

    suspend operator fun invoke(plateNumber: String): List<Car>
}