package com.noemi.carsreloaded.model.remote

import androidx.annotation.Keep
import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.model.local.CarLocation
import com.noemi.carsreloaded.model.local.CarModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RemoteCar(
    @SerialName("id")
    val id: Int,

    @SerialName("plateNumber")
    val plateNumber: String,

    @SerialName("location")
    val location: RemoteCarLocation,

    @SerialName("model")
    val model: RemoteCarModel,

    @SerialName("batteryPercentage")
    val batteryPercentage: Int,

    @SerialName("batteryEstimatedDistance")
    val batteryEstimatedDistance: Int
)

internal  fun RemoteCar.toCar(): Car {
    return Car(
        id = id,
        plateNumber = plateNumber,
        location = CarLocation(
            longitude = location.longitude,
            latitude = location.latitude,
            address = location.address
        ),
        model = CarModel(id = model.id, title = model.title, photoUrl = model.photoUrl),
        batteryPercentage = batteryPercentage,
        batteryEstimatedDistance = batteryEstimatedDistance
    )
}