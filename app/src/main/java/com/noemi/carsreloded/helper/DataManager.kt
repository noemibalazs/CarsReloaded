package com.noemi.carsreloded.helper

interface DataManager {

    suspend fun setLastKnownLatitude(latitude: Double)

    suspend fun getLastKnownLatitude(): Double

    suspend fun setLastKnowLongitude(longitude: Double)

    suspend fun getLastKnownLongitude():Double
}