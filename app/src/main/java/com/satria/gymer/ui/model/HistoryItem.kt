package com.satria.gymer.ui.model

import android.os.Parcel
import android.os.Parcelable

data class HistoryItem(
    val title: String,
    val duration: String,
    val sets: Int,
    val weight: String
) : Parcelable {
    // Constructor untuk membuat objek dari Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    // Menulis data ke Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(duration)
        parcel.writeInt(sets)
        parcel.writeString(weight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<HistoryItem> {
            override fun createFromParcel(parcel: Parcel): HistoryItem {
                return HistoryItem(parcel)
            }

            override fun newArray(size: Int): Array<HistoryItem?> {
                return arrayOfNulls(size)
            }
        }
    }
}
