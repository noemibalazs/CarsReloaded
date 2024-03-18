package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car

interface UseCaseSaveCars {

    suspend operator fun invoke(cars: List<Car>)
}