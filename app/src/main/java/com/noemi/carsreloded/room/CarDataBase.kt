package com.noemi.carsreloded.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.noemi.carsreloded.model.converters.CarLocationConverter
import com.noemi.carsreloded.model.converters.CarModelConverter
import com.noemi.carsreloded.model.local.Car
import com.noemi.carsreloded.util.CAR_DB

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