package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car

interface UseCaseLoadCars  {

    suspend operator fun invoke(): Result<List<Car>>
}