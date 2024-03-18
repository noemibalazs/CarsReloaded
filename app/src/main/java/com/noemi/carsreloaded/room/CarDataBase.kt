package com.noemi.carsreloaded.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.noemi.carsreloaded.model.converters.CarLocationConverter
import com.noemi.carsreloaded.model.converters.CarModelConverter
import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloaded.util.CAR_DB

@Database(entities = [Car::class], version = 1, exportSchema = false)
@TypeConverters(CarLocationConverter::class, CarModelConverter::class)
abstract class CarDataBase : RoomDatabase() {

    abstract fun carDAO(): CarDAO

    companion object {
        fun initCarDataBase(context: Context): CarDataBase {
            return Room.databaseBuilder(context, CarDataBase::class.java, CAR_DB).build()
        }
    }
}