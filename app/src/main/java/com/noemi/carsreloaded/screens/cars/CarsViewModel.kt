package com.noemi.carsreloaded.screens.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.usecase.LoadCarsUseCase
import com.noemi.carsreloaded.usecase.SaveCarsUseCase
import kotlinx.coroutines.launch

class CarsViewModel(
    private val loadCarsUseCase: LoadCarsUseCase,
    private val useCarsSaveCars: SaveCarsUseCase
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

            loadCarsUseCase.invoke()
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