package com.noemi.carsreloded.model.local

import kotlinx.serialization.Serializable

@Serializable
data class CarLocation(
    val latitude: Double,
    val longitude: Double,
    val address: String
)