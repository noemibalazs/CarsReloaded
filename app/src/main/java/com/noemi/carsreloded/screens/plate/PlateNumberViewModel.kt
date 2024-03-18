package com.noemi.carsreloded.screens.plate

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.usecase.UseCaseFilterByPlateNumber
import kotlinx.coroutines.launch

class PlateNumberViewModel(private val useCaseFilterByPlateNumber: UseCaseFilterByPlateNumber) : ViewModel() {

    private val _cars = MutableLiveData<List<Car>>()
    val cars: LiveData<List<Car>> = _cars

    private val _hideKeyboard = MutableLiveData<Boolean>()
    val hideKeyBoard: LiveData<Boolean> = _hideKeyboard

    val plateNumber = ObservableField("")

    fun onFilterByPlateNumberClicked() {
        _hideKeyboard.value = true

        viewModelScope.launch {
            val cars = useCaseFilterByPlateNumber.invoke(plateNumber.get().plus("%"))
            _cars.value = cars
            plateNumber.set("")
        }
    }
}