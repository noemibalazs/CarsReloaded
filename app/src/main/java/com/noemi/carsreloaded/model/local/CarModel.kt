package com.noemi.carsreloaded.model.local

import kotlinx.serialization.Serializable

@Serializable
data class CarModel(
    val id: Int,
    val title: String,
    val photoUrl: String
)