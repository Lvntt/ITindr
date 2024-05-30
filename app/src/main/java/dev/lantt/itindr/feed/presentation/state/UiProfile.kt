package dev.lantt.itindr.feed.presentation.state

import android.os.Parcel
import android.os.Parcelable

data class UiProfile(
    val id: String = "",
    val name: String = "",
    val aboutMyself: String = "",
    val avatarUrl: String? = null,
    val topics: List<UiTopic> = emptyList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.createTypedArrayList(UiTopic) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(aboutMyself)
        parcel.writeString(avatarUrl)
        parcel.writeTypedList(topics)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UiProfile> {
        override fun createFromParcel(parcel: Parcel): UiProfile {
            return UiProfile(parcel)
        }

        override fun newArray(size: Int): Array<UiProfile?> {
            return arrayOfNulls(size)
        }
    }

}