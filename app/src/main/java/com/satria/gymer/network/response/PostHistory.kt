package com.satria.gymer.network.response

data class PostHistory(
    val userId: Int,
    val exerciseId: Int,
    val duration: String,
    val calories: String
)
