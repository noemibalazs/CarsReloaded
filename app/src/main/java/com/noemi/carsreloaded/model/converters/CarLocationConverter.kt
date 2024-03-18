package com.noemi.carsreloaded.model.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.noemi.carsreloaded.model.local.CarLocation

class CarLocationConverter {

    @TypeConverter
    fun toString(carLocation: CarLocation): String {
        return Gson().toJson(carLocation)
    }

    @TypeConverter
    fun fromString(json: String): CarLocation {
        val type = object : TypeToken<CarLocation>() {}.type
        return Gson().fromJson(json, type)
    }
}