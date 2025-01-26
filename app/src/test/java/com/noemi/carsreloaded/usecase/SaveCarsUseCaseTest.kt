package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveCarsUseCaseTest {

    private var carsRepository: CarRepository = mockk()
    private val car = mockk<Car>()
    private val cars = listOf(car)

    private lateinit var saveCarsUseCase: SaveCarsUseCase

    @Before
    fun setUp() {
        saveCarsUseCase = SaveCarsUseCase(carsRepository)
    }

    @Test
    fun `test save cars should be ok`() = runBlocking {
        coEvery { carsRepository.saveCars(cars) } just runs

        saveCarsUseCase.invoke(cars)

        coVerify { carsRepository.saveCars(cars) }
    }
}