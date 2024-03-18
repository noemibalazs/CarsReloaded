package com.noemi.carsreloaded.remotedatasource

import com.noemi.carsreloaded.model.network.NetworkResult
import com.noemi.carsreloaded.model.remote.RemoteCar

interface CarRemoteDataSource {

    suspend fun getRemoteCars(): NetworkResult<List<RemoteCar>>
}