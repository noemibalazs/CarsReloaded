package com.noemi.carsreloaded.helper

import android.content.Context
import com.noemi.carsreloaded.util.LAST_KNOW_LATITUDE
import com.noemi.carsreloaded.util.LAST_KNOW_LONGITUDE
import java.lang.Double.longBitsToDouble

class DataManagerImpl(context: Context) : DataManager {

    private val sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    override suspend fun setLastKnownLatitude(latitude: Double) =
        sharedPreferences.edit()
            .putLong(LAST_KNOW_LATITUDE, java.lang.Double.doubleToRawLongBits(latitude)).apply()

    override suspend fun getLastKnownLatitude(): Double =
        longBitsToDouble(sharedPreferences.getLong(LAST_KNOW_LATITUDE, 0))

    override suspend fun setLastKnowLongitude(longitude: Double) =
        sharedPreferences.edit()
            .putLong(LAST_KNOW_LONGITUDE, java.lang.Double.doubleToRawLongBits(longitude)).apply()

    override suspend fun getLastKnownLongitude(): Double =
        longBitsToDouble(sharedPreferences.getLong(LAST_KNOW_LONGITUDE, 0))
}