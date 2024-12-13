package com.satria.gymer.network.response

import com.google.gson.annotations.SerializedName

data class RegisterResp(
    val status: String,
    val message: String,
    val id: Int, // Tambahkan jika API mengembalikan ID user
    val name: String,
    val email: String,
    val tinggi_badan: Float,
    val berat_badan: Float,
    @SerializedName("phone_number") val phone_number: String
)