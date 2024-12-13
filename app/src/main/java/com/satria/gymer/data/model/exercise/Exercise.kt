package com.satria.gymer.data.model.exercise

import com.google.gson.annotations.SerializedName

data class Exercise(
    @SerializedName("id_exercise") val idExercise: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("nama_alat") val equipmentName: String,
    @SerializedName("bagian_tubuh") val bodyPart: String,
    @SerializedName("gambar") val imageUrl: String,
    @SerializedName("nama_exercise") val exerciseName: String,
    @SerializedName("description") val description: String,
    @SerializedName("how_to_do") val howToDo: String,
    @SerializedName("training_tips") val trainingTips: String
)