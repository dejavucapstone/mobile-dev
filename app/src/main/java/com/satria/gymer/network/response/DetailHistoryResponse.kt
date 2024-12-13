package com.satria.gymer.network.response

import com.google.gson.annotations.SerializedName

data class DetailHistoryResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class Data(

	@field:SerializedName("detil_history")
	val detilHistory: DetilHistory? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id_history")
	val idHistory: Int? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null
)

data class DetilHistory(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("exercises")
	val exercises: List<ExercisesItem?>? = null
)

data class SetsItem(

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("repetitions")
	val repetitions: Int? = null
)

data class ExercisesItem(

	@field:SerializedName("sets")
	val sets: List<SetsItem?>? = null,

	@field:SerializedName("exercise_name")
	val exerciseName: String? = null
)
