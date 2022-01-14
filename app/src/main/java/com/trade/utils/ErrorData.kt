package com.trade.utils

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorData(
    @SerializedName("code") var code: String?,
    @SerializedName("message") var message: String?,
    @SerializedName("desc") val desc: String
) {
    companion object
}

fun ErrorData.Companion.empty() = ErrorData("", "", "")
