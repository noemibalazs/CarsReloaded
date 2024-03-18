package com.noemi.carsreloded.screens.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.usecase.UseCaseLoadCars
import com.noemi.carsreloded.usecase.UseCaseSaveCars
import kotlinx.coroutines.launch

class CarsViewModel(
    private val useCaseLoadCars: UseCaseLoadCars,
    private val useCarsSaveCars: UseCaseSaveCars
) : ViewModel() {

    private val _cars = MutableLiveData<List<Car>>()
    val cars: LiveData<List<Car>> = _cars

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadCars() {
        viewModelScope.launch {
            _isLoading.value = true

            useCaseLoadCars.invoke()
                .onSuccess { cars ->
                    _cars.value = cars
                    _isLoading.value = false
                    useCarsSaveCars.invoke(cars)
                }
                .onFailure {
                    _isLoading.value = false
                    _errorMessage.value = it.message ?: "Something went wrong, try it again!"
                }
        }
    }
}