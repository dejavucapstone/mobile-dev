package com.satria.gymer.data.model.history

import com.google.gson.annotations.SerializedName

data class SetDetail(
    @SerializedName("repetitions") val repetitions: Int,
    @SerializedName("weight") val weight: Int
)