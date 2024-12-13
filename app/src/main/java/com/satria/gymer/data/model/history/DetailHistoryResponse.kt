package com.satria.gymer.data.model.history

import com.google.gson.annotations.SerializedName

data class DetailHistoryResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: HistoryData
)
