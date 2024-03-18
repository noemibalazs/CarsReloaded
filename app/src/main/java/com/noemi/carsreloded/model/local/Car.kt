package com.noemi.carsreloded.model.local

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.noemi.carsreloded.model.converters.CarLocationConverter
import com.noemi.carsreloded.model.converters.CarModelConverter
import com.noemi.carsreloded.util.CAR_TABLE
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Entity(tableName = CAR_TABLE)
data class Car(
    @PrimaryKey
    val id: Int,

    val plateNumber: String,

    @TypeConverters(CarLocationConverter::class)
    val location: CarLocation,

    @TypeConverters(CarModelConverter::class)
    val model: CarModel,

    val batteryPercentage: Int,

    val batteryEstimatedDistance: Int
)