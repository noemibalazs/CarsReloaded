package com.noemi.carsreloded.screens.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.usecase.UseCaseGetCars
import kotlinx.coroutines.launch

class MapViewModel(private val useCaseGetCars: UseCaseGetCars) : ViewModel() {

    private val _cars = MutableLiveData<List<Car>>()
    val cars: LiveData<List<Car>> = _cars

    fun loadCars() {
        viewModelScope.launch {
            val cars = useCaseGetCars.invoke()
            _cars.value = cars
        }
    }
}