package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car

interface UseCaseGetCars {

    suspend operator fun invoke(): List<Car>
}