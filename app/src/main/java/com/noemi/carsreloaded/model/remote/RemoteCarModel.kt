package com.noemi.carsreloaded.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCarModel(

    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("photoUrl")
    val photoUrl: String
)