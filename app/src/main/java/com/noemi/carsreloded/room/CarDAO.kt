package com.noemi.carsreloded.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.util.CAR_TABLE

@Dao
interface CarDAO {

    @Query("SELECT * FROM $CAR_TABLE")
    suspend fun getCars(): List<Car>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCars(cars: List<Car>)

    @Query("SELECT * FROM $CAR_TABLE WHERE plateNumber LIKE:number")
    suspend fun filteredCarsByPlateNumber(number: String): List<Car>

    @Query("SELECT * FROM $CAR_TABLE WHERE batteryPercentage >= :percentage ORDER BY id")
    suspend fun filteredCarsByRemainingBattery(percentage: Int): List<Car>
}