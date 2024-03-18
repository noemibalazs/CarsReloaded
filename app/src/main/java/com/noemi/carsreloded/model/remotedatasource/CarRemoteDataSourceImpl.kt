package com.noemi.carsreloded.model.remotedatasource

import com.noemi.carsreloded.helper.ApiResponseHelper
import com.noemi.carsreloded.model.network.NetworkResult
import com.noemi.carsreloded.model.remote.RemoteCar
import com.noemi.carsreloded.network.CarServiceAPI

class CarRemoteDataSourceImpl(
    private val carServiceAPI: CarServiceAPI,
    private val apiResponseHelper: ApiResponseHelper
) : CarRemoteDataSource {

    override suspend fun getRemoteCars(): NetworkResult<List<RemoteCar>> =
        apiResponseHelper.handleApiResponse { carServiceAPI.getCars() }
}