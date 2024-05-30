package dev.lantt.itindr.feed.presentation.state

import android.os.Parcel
import android.os.Parcelable

data class UiTopic(
    val id: String,
    val title: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
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
