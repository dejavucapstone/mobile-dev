package com.satria.gymer.data.model.resetpassword

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("email") val email: String,
    @SerializedName("resetCode") val resetCode: String,
    @SerializedName("newPassword") val newPassword: String
)