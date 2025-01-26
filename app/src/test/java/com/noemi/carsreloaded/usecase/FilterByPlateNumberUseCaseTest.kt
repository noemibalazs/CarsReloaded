package com.noemi.carsreloaded.usecase

import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.repository.CarRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FilterByPlateNumberUseCaseTest {

    private val carsRepository: CarRepository = mockk()
    private val car1 = mockk<Car>()
    private val car2 = mockk<Car>()
    private val plate1 = "TEST12"
    private val plate2 = "PROBE3"
    private val filterTest = "TEST"
    private val filterNew = "NEW"

    private lateinit var saveCarsUseCase: SaveCarsUseCase
    private lateinit var filterByPlateNumberUseCase: FilterByPlateNumberUseCase

    @Before
    fun setUp() {
        saveCarsUseCase = SaveCarsUseCase(carsRepository)
        filterByPlateNumberUseCase = FilterByPlateNumberUseCase(carsRepository)
    }

    @Test
    fun `test filter by plate number returns one result`() = runBlocking {

        coEvery { car1.plateNumber } returns plate1
        coEvery { car2.plateNumber } returns plate2

        coEvery { carsRepository.saveCars(listOf(car1, car2)) } just runs
        coEvery { carsRepository.filteredByPlateNumber(filterTest) } returns listOf(car1)

        saveCarsUseCase.invoke(listOf(car1, car2))
        filterByPlateNumberUseCase.invoke(filterTest)

        coVerifyAll {
            carsRepository.saveCars(listOf(car1, car2))
            carsRepository.filteredByPlateNumber(filterTest)
        }
    }

    @Test
    fun `test filter by plate number returns empty result`() = runBlocking {

        coEvery { car1.plateNumber } returns plate1

        coEvery { carsRepository.saveCars(listOf(car1)) } just runs
        coEvery { carsRepository.filteredByPlateNumber(filterNew) } returns emptyList()

        saveCarsUseCase.invoke(listOf(car1))
        filterByPlateNumberUseCase.invoke(filterNew)

        coVerifyAll {
            carsRepository.saveCars(listOf(car1))
            carsRepository.filteredByPlateNumber(filterNew)
        }
    }
}