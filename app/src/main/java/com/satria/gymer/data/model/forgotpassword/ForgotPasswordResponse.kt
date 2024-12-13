package com.satria.gymer.data.model.forgotpassword

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)