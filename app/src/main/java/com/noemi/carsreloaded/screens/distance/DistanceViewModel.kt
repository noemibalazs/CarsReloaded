package com.noemi.carsreloaded.screens.distance

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noemi.carsreloaded.helper.DataManager
import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.usecase.UseCaseGetCars
import com.noemi.carsreloaded.util.meterToKm
import kotlinx.coroutines.launch
import java.util.*

class DistanceViewModel(
    private val useCaseGetCars: UseCaseGetCars,
    private val dataManager: DataManager
) : ViewModel() {

    private val _cars = MutableLiveData<List<Car>>()
    val cars: LiveData<List<Car>> = _cars

    private val userLocation = Location("User location")
    private val carPosition = Location("Car position")

    fun saveUserLocationCoordinates(location: Location) {
        viewModelScope.launch {
            dataManager.setLastKnownLatitude(location.latitude)
            dataManager.setLastKnowLongitude(location.longitude)
        }
    }

    fun loadCars() {
        viewModelScope.launch {
            val sorted: SortedMap<Double, Car> = sortedMapOf()

            userLocation.latitude = dataManager.getLastKnownLatitude()
            userLocation.longitude = dataManager.getLastKnownLongitude()

            useCaseGetCars.invoke().forEach { car ->

                carPosition.latitude = car.location.latitude
                carPosition.longitude = car.location.longitude

                val distance = userLocation.distanceTo(carPosition)
                val km = distance.meterToKm()
                sorted[km] = car

                _cars.value = sorted.values.toList()
            }
        }
    }
}