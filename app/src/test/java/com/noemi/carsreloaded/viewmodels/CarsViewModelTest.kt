package com.noemi.carsreloaded.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.screens.cars.CarsViewModel
import com.noemi.carsreloaded.usecase.UseCaseLoadCars
import com.noemi.carsreloaded.usecase.UseCaseSaveCars
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CarsViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()

    private val useCaseLoadCars: UseCaseLoadCars = mockk()
    private val useCarsSaveCars: UseCaseSaveCars = mockk()
    private val carsObserver: Observer<List<Car>> = mockk()
    private val errorObserver: Observer<String> = mockk()
    private val progressObserver: Observer<Boolean> = mockk()

    private val progressCaptor = mutableListOf<Boolean>()
    private val errorCaptor = mutableListOf<String>()
    private val carsCaptor = mutableListOf<List<Car>>()

    private val car: Car = mockk()
    private val cars = listOf(car)
    private val error = "error"

    private lateinit var viewModel: CarsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = CarsViewModel(
            useCaseLoadCars = useCaseLoadCars,
            useCarsSaveCars = useCarsSaveCars
        )

        progressCaptor.clear()
        errorCaptor.clear()
        carsCaptor.clear()

        coEvery { carsObserver.onChanged(capture(carsCaptor)) } just runs
        coEvery { progressObserver.onChanged(capture(progressCaptor)) } just runs
        coEvery { errorObserver.onChanged(capture(errorCaptor)) } just runs

        viewModel.cars.observeForever(carsObserver)
        viewModel.isLoading.observeForever(progressObserver)
        viewModel.errorMessage.observeForever(errorObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `test load cars and should be successful`() = runBlocking {
        coEvery { useCaseLoadCars.invoke() } returns Result.success(cars)
        coEvery { useCarsSaveCars.invoke(cars) } just runs

        viewModel.loadCars()

        coVerify { useCaseLoadCars.invoke() }
        coVerify { useCarsSaveCars.invoke(cars) }

        coVerify { progressObserver.onChanged(true) }
        coVerify { carsObserver.onChanged(listOf(car)) }
        coVerify { progressObserver.onChanged(false) }
    }

    @Test
    fun `test load cars and should failed`() = runBlocking {
        coEvery { useCaseLoadCars.invoke() } returns Result.failure(Throwable(error))

        viewModel.loadCars()

        coVerify { useCaseLoadCars.invoke() }

        coVerify { progressObserver.onChanged(true) }
        coVerify { errorObserver.onChanged(error) }
        coVerify { progressObserver.onChanged(false) }
    }
}