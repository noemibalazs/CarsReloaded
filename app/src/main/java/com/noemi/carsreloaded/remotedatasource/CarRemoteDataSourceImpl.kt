package com.noemi.carsreloaded.remotedatasource

import com.noemi.carsreloaded.helper.ApiResponseHelper
import com.noemi.carsreloaded.model.network.NetworkResult
import com.noemi.carsreloaded.model.remote.RemoteCar
import com.noemi.carsreloaded.network.CarServiceAPI

class CarRemoteDataSourceImpl(
    private val carServiceAPI: CarServiceAPI,
    private val apiResponseHelper: ApiResponseHelper
) : CarRemoteDataSource {

    override suspend fun getRemoteCars(): NetworkResult<List<RemoteCar>> =
        apiResponseHelper.handleApiResponse { carServiceAPI.getCars() }
}