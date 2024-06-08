package dev.lantt.itindr.chats.chat.presentation.state

import android.os.Parcel
import android.os.Parcelable

data class UiChat(
    val id: String,
    val title: String,
    val avatar: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UiChat> {
        override fun createFromParcel(parcel: Parcel): UiChat {
            return UiChat(parcel)
        }

        override fun newArray(size: Int): Array<UiChat?> {
            return arrayOfNulls(size)
        }
    }
}