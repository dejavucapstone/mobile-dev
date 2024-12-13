package com.satria.gymer.network.response

data class ForgotPassResponse(
    val status: String,
    val message: ForgotPassMessage
)

data class ForgotPassMessage(
    val message: String
)
