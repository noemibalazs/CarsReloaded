package com.noemi.carsreloded.usecase

import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.repository.CarRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UseCaseFilterByPlateNumberImplTest {

    private val carsRepository: CarRepository = mockk()
    private val car1 = mockk<Car>()
    private val car2 = mockk<Car>()
    private val plate1 = "TEST12"
    private val plate2 = "PROBE3"
    private val filterTest = "TEST"
    private val filterNew = "NEW"

    private lateinit var useCaseSaveCars: UseCaseSaveCars
    private lateinit var useCaseFilterByPlateNumber: UseCaseFilterByPlateNumber

    @Before
    fun setUp() {
        useCaseSaveCars = UseCaseSaveCarsImpl(carsRepository)
        useCaseFilterByPlateNumber = UseCaseFilterByPlateNumberImpl(carsRepository)
    }

    @Test
    fun `test filter by plate number returns one result`() = runBlocking {

        coEvery { car1.plateNumber } returns plate1
        coEvery { car2.plateNumber } returns plate2

        coEvery { carsRepository.saveCars(listOf(car1, car2)) } just runs
        coEvery { carsRepository.filteredByPlateNumber(filterTest) } returns listOf(car1)

        useCaseSaveCars.invoke(listOf(car1, car2))
        useCaseFilterByPlateNumber.invoke(filterTest)

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

        useCaseSaveCars.invoke(listOf(car1))
        useCaseFilterByPlateNumber.invoke(filterNew)

        coVerifyAll {
            carsRepository.saveCars(listOf(car1))
            carsRepository.filteredByPlateNumber(filterNew)
        }
    }
}