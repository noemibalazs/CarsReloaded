package com.noemi.carsreloded.usecase

import com.noemi.carsreloded.model.local.Car

interface UseCaseLoadCars  {

    suspend operator fun invoke(): Result<List<Car>>
}