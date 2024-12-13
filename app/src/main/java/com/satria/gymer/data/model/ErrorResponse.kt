package com.satria.gymer.data.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)