package com.cafe.data.source.mapper

import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class HttpErrorMapper @Inject constructor(private val gson: Gson) {

    fun mapToErrorModel(throwable: Throwable): Error? {
        return when (throwable) {
            is HttpException -> {
                getHttpError(throwable)
            }
            is SocketTimeoutException -> {
                HttpError.TimeOut
            }
            is IOException -> {
                HttpError.ConnectionFailed
            }
            else -> null
        }
    }

    private fun getHttpError(httpException: HttpException): Error {
        return when (val code = httpException.code()) {
            401 -> HttpError.UnAuthorized
            else -> {
                val errorBody = httpException.response()?.errorBody()
                HttpError.InvalidResponse(code, errorBody?.string())
            }
        }
    }
}