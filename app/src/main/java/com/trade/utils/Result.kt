package com.trade.utils

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val httpCode: Int = 0, val code: String = "", val message: String = "", val desc: String = "") : Result<T>()
    class Loading<T> : Result<T>()
}

fun <T> Result<T>?.isNotLoading(): Boolean {
    return this !is Result.Loading
}
