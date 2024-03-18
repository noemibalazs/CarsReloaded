package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UseCaseFilterByBatteryPercentageImplTest {

    private val carsRepository: CarRepository = mockk()
    private val car1 = mockk<Car>()
    private val car2 = mockk<Car>()
    private val battery1 = 69
    private val battery2 = 81

    private lateinit var useCaseSaveCars: UseCaseSaveCars
    private lateinit var useCaseFilterByBatteryPercentage: UseCaseFilterByBatteryPercentage

    @Before
    fun setUp() {
        useCaseSaveCars = UseCaseSaveCarsImpl(carsRepository)
        useCaseFilterByBatteryPercentage = UseCaseFilterByBatteryPercentageImpl(carsRepository)
    }


    @Test
    fun `test filter by plate battery percentage returns one result`() = runBlocking {

        coEvery { car1.batteryPercentage } returns battery1
        coEvery { car2.batteryPercentage } returns battery2

        coEvery { carsRepository.saveCars(listOf(car1, car2)) } just runs
        coEvery { carsRepository.filteredByBatteryPercentage(72) } returns listOf(car1)

        useCaseSaveCars.invoke(listOf(car1, car2))
        useCaseFilterByBatteryPercentage.invoke(72)

        coVerifyAll {
            carsRepository.saveCars(listOf(car1, car2))
            carsRepository.filteredByBatteryPercentage(72)
        }
    }

    @Test
    fun `test filter by battery percentage returns empty result`() = runBlocking {

        coEvery { car1.batteryPercentage } returns battery1

        coEvery { carsRepository.saveCars(listOf(car1)) } just runs
        coEvery { carsRepository.filteredByBatteryPercentage(90) } returns emptyList()

        useCaseSaveCars.invoke(listOf(car1))
        useCaseFilterByBatteryPercentage.invoke(90)

        coVerifyAll {
            carsRepository.saveCars(listOf(car1))
            carsRepository.filteredByBatteryPercentage(90)
        }
    }
}