package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UseCaseGetCarsImplTest {

    private val carsRepository: CarRepository = mockk()
    private val car = mockk<Car>()

    private lateinit var useCaseGetCars: UseCaseGetCars

    @Before
    fun setUp() {
        useCaseGetCars = UseCaseGetCarsImpl(carsRepository)
    }

    @Test
    fun `test use case get cars returns list of cars`() = runBlocking {
        coEvery { carsRepository.getCars() } returns listOf(car)

        useCaseGetCars.invoke()

        coVerify { carsRepository.getCars() }
    }
}