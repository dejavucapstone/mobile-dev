package com.satria.gymer.data.model.register

import com.google.gson.annotations.SerializedName

data class LoginRegisterData(
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("password") val password: String,
    @SerializedName("email") val email: String,
    @SerializedName("reset_code") val resetCode: String?,
    @SerializedName("reset_code_expired") val resetCodeExpired: String?
)