package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LoadCarsUseCaseTest {

    private val carsRepository: CarRepository = mockk()
    private val car = mockk<Car>()

    private lateinit var loadCarsUseCase: LoadCarsUseCase

    @Before
    fun setUp() {
        loadCarsUseCase = LoadCarsUseCase(carsRepository)
    }

    @Test
    fun `test load remote cars should be successful`() = runBlocking {
        coEvery { carsRepository.loadRemoteCars() } returns Result.success(listOf(car))

        loadCarsUseCase.invoke()

        coVerify { carsRepository.loadRemoteCars() }
    }

    @Test
    fun `test load remote cars should be a failure`() = runBlocking {
        coEvery { carsRepository.loadRemoteCars() } returns Result.failure(Throwable("error"))

        loadCarsUseCase.invoke()

        coVerify { carsRepository.loadRemoteCars() }
    }
}