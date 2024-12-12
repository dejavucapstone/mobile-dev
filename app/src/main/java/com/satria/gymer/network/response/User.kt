package com.satria.gymer.network.response

data class User(
    val firstName: String,  // Nama depan
    val phone: String,      // Nomor telepon
    val email: String,      // Email
    val password: String,   // Password
    val confirmPassword: String // Konfirmasi password
)