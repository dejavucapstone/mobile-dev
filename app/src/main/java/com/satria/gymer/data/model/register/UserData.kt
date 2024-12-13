package com.satria.gymer.data.model.register

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("id_user") val idUser: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("berat_badan") val beratBadan: Double,
    @SerializedName("tinggi_badan") val tinggiBadan: Double,
    @SerializedName("email") val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("name") val name: String
)