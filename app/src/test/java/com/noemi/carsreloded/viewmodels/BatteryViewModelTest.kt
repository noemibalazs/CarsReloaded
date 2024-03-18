package com.noemi.carsreloded.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.screens.battery.BatteryViewModel
import com.noemi.carsreloded.usecase.UseCaseFilterByBatteryPercentage
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
class BatteryViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()

    private val useCase: UseCaseFilterByBatteryPercentage = mockk()
    private val carsObserver: Observer<List<Car>> = mockk()
    private val hideKeyBoardObserver: Observer<Boolean> = mockk()

    private val keyBoardCaptor = mutableListOf<Boolean>()
    private val carsCaptor = mutableListOf<List<Car>>()

    private val car: Car = mockk()
    private val cars = listOf(car)

    private lateinit var viewModel: BatteryViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = BatteryViewModel(useCase)

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
    fun `test filter by percentage and returns the expected result`() = runBlocking {
        viewModel.batteryPercentage.set("12")
        coEvery { useCase.invoke(viewModel.batteryPercentage.get()?.toIntOrNull() ?: 0) } returns cars

        viewModel.onFilterByPercentageClicked()

        coVerify { useCase.invoke(12) }
        coVerify { hideKeyBoardObserver.onChanged(true) }
        coVerify { carsObserver.onChanged(cars) }
        viewModel.batteryPercentage.set("")
    }

    @Test
    fun `test filter by percentage and returns empty list`() = runBlocking {
        viewModel.batteryPercentage.set("")
        coEvery { useCase.invoke(viewModel.batteryPercentage.get()?.toIntOrNull() ?: 0) } returns emptyList()

        viewModel.onFilterByPercentageClicked()

        coVerify { useCase.invoke(0) }
        coVerify { hideKeyBoardObserver.onChanged(true) }
        coVerify { carsObserver.onChanged(emptyList()) }
        viewModel.batteryPercentage.set("")
    }
}