package com.satria.gymer.data.model.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: RegisterData
)