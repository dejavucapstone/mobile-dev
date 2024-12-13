package com.satria.gymer.data.model.history

import com.google.gson.annotations.SerializedName

data class ExerciseHistory(
    @SerializedName("exercise_name") val exerciseName: String,
    @SerializedName("sets") val sets: List<SetDetail>
)