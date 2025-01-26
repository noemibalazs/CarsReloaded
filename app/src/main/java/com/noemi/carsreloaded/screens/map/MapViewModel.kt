package com.noemi.carsreloaded.screens.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.usecase.GetCarsUseCase
import kotlinx.coroutines.launch

class MapViewModel(private val getCarsUseCase: GetCarsUseCase) : ViewModel() {

    private val _cars = MutableLiveData<List<Car>>()
    val cars: LiveData<List<Car>> = _cars

    fun loadCars() {
        viewModelScope.launch {
            val cars = getCarsUseCase.invoke()
            _cars.value = cars
        }
    }
}