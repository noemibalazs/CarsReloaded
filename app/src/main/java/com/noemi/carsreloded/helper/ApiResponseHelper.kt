package com.noemi.carsreloded.helper

import com.noemi.carsreloded.model.network.NetworkResult
import retrofit2.Response

interface ApiResponseHelper {

    suspend fun <T:Any> handleApiResponse(response: suspend () -> Response<T>): NetworkResult<T>
}