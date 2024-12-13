package com.satria.gymer.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("token") val token: String
)