package com.noemi.carsreloded.usecase

import com.noemi.carsreloded.model.local.Car

interface UseCaseSaveCars {

    suspend operator fun invoke(cars: List<Car>)
}