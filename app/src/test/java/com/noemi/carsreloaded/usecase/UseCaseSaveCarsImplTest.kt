package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UseCaseSaveCarsImplTest {

    private var carsRepository: CarRepository = mockk()
    private val car = mockk<Car>()
    private val cars = listOf(car)

    private lateinit var useCaseSaveCars: UseCaseSaveCars

    @Before
    fun setUp() {
        useCaseSaveCars = UseCaseSaveCarsImpl(carsRepository)
    }

    @Test
    fun `test save cars should be ok`() = runBlocking {
        coEvery { carsRepository.saveCars(cars) } just runs

        useCaseSaveCars.invoke(cars)

        coVerify { carsRepository.saveCars(cars) }
    }
}