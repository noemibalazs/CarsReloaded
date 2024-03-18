package com.noemi.carsreloaded.helper

import com.noemi.carsreloaded.model.network.NetworkResult
import retrofit2.Response

interface ApiResponseHelper {

    suspend fun <T:Any> handleApiResponse(response: suspend () -> Response<T>): NetworkResult<T>
}