package com.satria.gymer.data.model.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("berat_badan") val beratBadan: Double,
    @SerializedName("tinggi_badan") val tinggiBadan: Double
)
