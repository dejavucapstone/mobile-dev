package com.satria.gymer.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: LoginData
)
