package com.satria.gymer.network.response

data class ResetPassResp(
    val status: String,
    val message: ResetPassMessage
)

data class ResetPassMessage(
    val message: String
)
