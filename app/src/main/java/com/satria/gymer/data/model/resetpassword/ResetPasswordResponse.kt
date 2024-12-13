package com.satria.gymer.data.model.resetpassword

import com.google.gson.annotations.SerializedName

data class ResetPasswordResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)