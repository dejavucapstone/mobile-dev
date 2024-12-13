package com.satria.gymer.network.response


enum class Gender {
    P, L
}
data class ReqBodyRegister(
    val name: String,
    val email: String,
    val phone_number: String, // Pastikan nama properti sesuai dengan yang diharapkan oleh API
    val password: String,
    val gender: String,       // Mengirimkan nilai gender sebagai String
    val tinggi_badan: Float,
    val berat_badan: Float

)
