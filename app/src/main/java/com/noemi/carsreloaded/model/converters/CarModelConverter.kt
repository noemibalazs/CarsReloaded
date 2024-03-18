package com.noemi.carsreloaded.model.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.noemi.carsreloaded.model.local.CarModel

class CarModelConverter {

    @TypeConverter
    fun toString(carModel: CarModel): String {
        return Gson().toJson(carModel)
    }

    @TypeConverter
    fun fromString(json: String): CarModel {
        val type = object : TypeToken<CarModel>() {}.type
        return Gson().fromJson(json, type)
    }
}