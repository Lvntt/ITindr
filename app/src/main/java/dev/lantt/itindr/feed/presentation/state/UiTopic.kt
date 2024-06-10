package dev.lantt.itindr.feed.presentation.state

import android.os.Parcel
import android.os.Parcelable

data class UiTopic(
    val id: String,
    val title: String,
    val isSelected: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UiTopic> {
        override fun createFromParcel(parcel: Parcel): UiTopic {
            return UiTopic(parcel)
        }

        override fun newArray(size: Int): Array<UiTopic?> {
            return arrayOfNulls(size)
        }
    }
}
