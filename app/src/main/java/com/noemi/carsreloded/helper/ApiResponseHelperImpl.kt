package com.noemi.carsreloded.helper

import com.noemi.carsreloded.model.network.NetworkResult
import com.orhanobut.logger.Logger
import org.koin.core.logger.KOIN_TAG
import retrofit2.HttpException
import retrofit2.Response

class ApiResponseHelperImpl : ApiResponseHelper {

    override suspend fun <T : Any> handleApiResponse(response: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val apiResponse = response()
            when (apiResponse.isSuccessful && apiResponse.body() != null) {
                true -> NetworkResult.Success(apiResponse.body()!!)
                else -> NetworkResult.Error(
                    apiResponse.errorBody()?.string() ?: "handleApiResponse() error - code: ${apiResponse.code()}, message: ${apiResponse.message()}"
                )
            }
        } catch (e: HttpException) {
            Logger.e(KOIN_TAG, "handleApiResponse() http exception: ${e.message()}")
            NetworkResult.Error(e.message())
        } catch (e: Exception) {
            Logger.e(KOIN_TAG, "handleApiResponse() exception: ${e.message}")
            NetworkResult.Failure(e)
        }
    }
}