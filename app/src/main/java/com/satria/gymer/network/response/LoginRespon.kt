package com.satria.gymer.network.response

data class LoginResp(
    val status: String,
    val message: String,
    val data: TokenLogin
)

data class TokenLogin(
    val token: String
)
