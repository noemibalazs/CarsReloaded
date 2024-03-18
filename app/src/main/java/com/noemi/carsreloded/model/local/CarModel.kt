package com.noemi.carsreloded.model.local

import kotlinx.serialization.Serializable

@Serializable
data class CarModel(
    val id: Int,
    val title: String,
    val photoUrl: String
)