package com.noemi.carsreloded.di

import com.noemi.carsreloded.helper.*
import com.noemi.carsreloded.network.CarServiceAPI
import com.noemi.carsreloded.model.remotedatasource.CarRemoteDataSource
import com.noemi.carsreloded.model.remotedatasource.CarRemoteDataSourceImpl
import com.noemi.carsreloded.repository.CarRepository
import com.noemi.carsreloded.repository.CarRepositoryImpl
import com.noemi.carsreloded.room.CarDataBase
import com.noemi.carsreloded.screens.battery.BatteryViewModel
import com.noemi.carsreloded.screens.cars.CarsViewModel
import com.noemi.carsreloded.screens.map.MapViewModel
import com.noemi.carsreloded.screens.distance.DistanceViewModel
import com.noemi.carsreloded.screens.plate.PlateNumberViewModel
import com.noemi.carsreloded.usecase.*
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
    single<UseCaseLoadCars> { UseCaseLoadCarsImpl(carRepository = get()) }
    single<UseCaseSaveCars> { UseCaseSaveCarsImpl(carRepository = get()) }
    single<UseCaseFilterByBatteryPercentage> { UseCaseFilterByBatteryPercentageImpl(carRepository = get()) }
    single<UseCaseFilterByPlateNumber> { UseCaseFilterByPlateNumberImpl(carRepository = get()) }
    single<UseCaseGetCars> { UseCaseGetCarsImpl(carRepository = get()) }
}

val viewModelsModule = module {
    viewModelOf(::CarsViewModel)
    viewModelOf(::BatteryViewModel)
    viewModelOf(::PlateNumberViewModel)
    viewModelOf(::DistanceViewModel)
    viewModelOf(::MapViewModel)
}