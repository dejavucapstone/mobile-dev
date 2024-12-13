package com.satria.gymer.data.model.exercise

import com.google.gson.annotations.SerializedName

data class ExerciseResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Exercise>
)