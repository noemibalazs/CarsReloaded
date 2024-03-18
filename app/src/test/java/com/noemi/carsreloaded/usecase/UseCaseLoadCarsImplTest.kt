package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UseCaseLoadCarsImplTest {

    private val carsRepository: CarRepository = mockk()
    private val car = mockk<Car>()

    private lateinit var useCaseLoadCars: UseCaseLoadCars

    @Before
    fun setUp() {
        useCaseLoadCars = UseCaseLoadCarsImpl(carsRepository)
    }

    @Test
    fun `test load remote cars should be successful`() = runBlocking {
        coEvery { carsRepository.loadRemoteCars() } returns Result.success(listOf(car))

        useCaseLoadCars.invoke()

        coVerify { carsRepository.loadRemoteCars() }
    }

    @Test
    fun `test load remote cars should be a failure`() = runBlocking {
        coEvery { carsRepository.loadRemoteCars() } returns Result.failure(Throwable("error"))

        useCaseLoadCars.invoke()

        coVerify { carsRepository.loadRemoteCars() }
    }
}