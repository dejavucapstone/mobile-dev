package com.satria.gymer.network.response

data class ReqBodyResetPass(
    val email: String,
    val password: String,
    val token: String
)
