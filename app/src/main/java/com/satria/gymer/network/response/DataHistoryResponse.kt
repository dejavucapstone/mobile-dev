package com.satria.gymer.network.response


data class DataHistories(
	val id: Int,
	val userId: Int,
	val exerciseId: Int,
	val duration: String,
	val calories: String
)
