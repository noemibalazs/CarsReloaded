package com.noemi.carsreloded.usecase

import com.noemi.carsreloded.model.local.Car

interface UseCaseGetCars {

    suspend operator fun invoke(): List<Car>
}