package com.satria.gymer.data.model.register

import android.service.autofill.UserData
import com.google.gson.annotations.SerializedName

data class RegisterData(
    @SerializedName("loginData") val loginData: List<LoginRegisterData>,
    @SerializedName("userData") val userData: List<UserData>
)