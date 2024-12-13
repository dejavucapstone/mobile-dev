package com.satria.gymer.data.model.history

import com.google.gson.annotations.SerializedName

data class HistoryData(
    @SerializedName("id_history") val historyId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("detil_history") val detailHistory: DetailHistory,
    @SerializedName("id_user") val userId: Int
)