package dev.androi.bestbuy.utils

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Failure(val throwable: Throwable) : ApiResult<Nothing>()
}

fun <T> success(data: T): ApiResult<T> = ApiResult.Success(data)
fun failure(t: Throwable): ApiResult<Nothing> = ApiResult.Failure(t)