package com.satria.gymer.network.response

import com.google.gson.annotations.SerializedName

data class ExcerciseResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null

)

data class DataItem(

	@field:SerializedName("nama_exercise")
	val namaExercise: String? = null,

	@field:SerializedName("how_to_do")
	val howToDo: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("nama_alat")
	val namaAlat: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("training_tips")
	val trainingTips: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("id_exercise")
	val idExercise: Int? = null,

	@field:SerializedName("bagian_tubuh")
	val bagianTubuh: String? = null
)
