package com.satria.gymer.data.model.history

import com.google.gson.annotations.SerializedName

data class DetailHistory(
    @SerializedName("date") val date: String,
    @SerializedName("duration") val duration: String,
    @SerializedName("exercises") val exerciseHistories: List<ExerciseHistory>
)