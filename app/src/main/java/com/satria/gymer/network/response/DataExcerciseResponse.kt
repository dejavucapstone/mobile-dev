package com.satria.gymer.network.response

import retrofit2.http.Url

data class DataExercises(
	val id: Int,
	val name: String,
	val description: String,
	val imageUrl: Url,
	var isFavorite: Boolean
) {


}

