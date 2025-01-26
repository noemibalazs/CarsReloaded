package com.noemi.carsreloaded.di

import com.noemi.carsreloaded.helper.*
import com.noemi.carsreloaded.network.CarServiceAPI
import com.noemi.carsreloaded.remotedatasource.CarRemoteDataSource
import com.noemi.carsreloaded.remotedatasource.CarRemoteDataSourceImpl
import com.noemi.carsreloaded.repository.CarRepository
import com.noemi.carsreloaded.repository.CarRepositoryImpl
import com.noemi.carsreloaded.room.CarDataBase
import com.noemi.carsreloaded.screens.battery.BatteryViewModel
import com.noemi.carsreloaded.screens.cars.CarsViewModel
import com.noemi.carsreloaded.screens.map.MapViewModel
import com.noemi.carsreloaded.screens.distance.DistanceViewModel
import com.noemi.carsreloaded.screens.plate.PlateNumberViewModel
import com.noemi.carsreloaded.usecase.*
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val apiHelperModule = module {
    single<ApiResponseHelper> { ApiResponseHelperImpl() }
}

val dataManagerModule = module {
    single<DataManager> {
        DataManagerImpl(
            context = androidApplication().applicationContext
        )
    }
}

val dispatcherModule = module {
    single<CarDispatcher> { CarDispatcherImpl() }
}

val carNetworkModule = module {
    single { CarServiceAPI.initCarService() }
}

val carDaoModule = module {
    single { CarDataBase.initCarDataBase(androidApplication().applicationContext).carDAO() }
}

val carRemoteDataSource = module {
    single<CarRemoteDataSource> {
        CarRemoteDataSourceImpl(
            carServiceAPI = get(),
            apiResponseHelper = get()
        )
    }
}

val carRepositoryModule = module {
    single<CarRepository> {
        CarRepositoryImpl(
            carDAO = get(),
            carRemoteDataSource = get(),
            carDispatcher = get()
        )
    }
}

val useCasesModule = module {
    single<LoadCarsUseCase> { LoadCarsUseCase(carRepository = get()) }
    single<SaveCarsUseCase> { SaveCarsUseCase(carRepository = get()) }
    single<FilterByBatteryPercentageUseCase> { FilterByBatteryPercentageUseCase(carRepository = get()) }
    single<FilterByPlateNumberUseCase> { FilterByPlateNumberUseCase(carRepository = get()) }
    single<GetCarsUseCase> { GetCarsUseCase(carRepository = get()) }
}

val viewModelsModule = module {
    viewModelOf(::CarsViewModel)
    viewModelOf(::BatteryViewModel)
    viewModelOf(::PlateNumberViewModel)
    viewModelOf(::DistanceViewModel)
    viewModelOf(::MapViewModel)
}