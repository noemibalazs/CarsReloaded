package com.noemi.carsreloaded.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.screens.plate.PlateNumberViewModel
import com.noemi.carsreloaded.usecase.FilterByPlateNumberUseCase
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
class PlateNumberViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()
    private val useCase: FilterByPlateNumberUseCase = mockk()

    private val carsObserver: Observer<List<Car>> = mockk()
    private val hideKeyBoardObserver: Observer<Boolean> = mockk()

    private val keyBoardCaptor = mutableListOf<Boolean>()
    private val carsCaptor = mutableListOf<List<Car>>()

    private val car: Car = mockk()
    private val cars = listOf(car)

    private lateinit var viewModel: PlateNumberViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = PlateNumberViewModel(
            filterByPlateNumberUseCase = useCase
        )

        carsCaptor.clear()
        keyBoardCaptor.clear()

        coEvery { carsObserver.onChanged(capture(carsCaptor)) } just runs
        coEvery { hideKeyBoardObserver.onChanged(capture(keyBoardCaptor)) } just runs

        viewModel.cars.observeForever(carsObserver)
        viewModel.hideKeyBoard.observeForever(hideKeyBoardObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test filter by plate number returns expected list`() = runBlocking {
        viewModel.plateNumber.set("TEST")
        coEvery { useCase.invoke(viewModel.plateNumber.get().plus("%")) } returns cars

        viewModel.onFilterByPlateNumberClicked()

        coVerify { useCase.invoke("TEST%") }
        coVerify { hideKeyBoardObserver.onChanged(true) }
        coVerify { carsObserver.onChanged(cars) }
        viewModel.plateNumber.set("")
    }

    @Test
    fun `test filter by plate number returns empty list`() = runBlocking {
        viewModel.plateNumber.set("")
        coEvery { useCase.invoke(viewModel.plateNumber.get().plus("%")) } returns emptyList()

        viewModel.onFilterByPlateNumberClicked()

        coVerify { useCase.invoke("%") }
        coVerify { hideKeyBoardObserver.onChanged(true) }
        coVerify { carsObserver.onChanged(emptyList()) }
        viewModel.plateNumber.set("")
    }
}