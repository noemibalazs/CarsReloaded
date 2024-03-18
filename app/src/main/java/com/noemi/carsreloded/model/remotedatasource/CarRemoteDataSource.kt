package com.noemi.carsreloded.model.remotedatasource

import com.noemi.carsreloded.model.network.NetworkResult
import com.noemi.carsreloded.model.remote.RemoteCar

interface CarRemoteDataSource {

    suspend fun getRemoteCars(): NetworkResult<List<RemoteCar>>
}