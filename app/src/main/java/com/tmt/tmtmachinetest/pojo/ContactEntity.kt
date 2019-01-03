package com.tmt.tmtmachinetest.pojo

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class ContactEntity(val imageUri: Uri, val name: String, val mobileNumber: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Uri::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(imageUri, flags)
        parcel.writeString(name)
        parcel.writeString(mobileNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContactEntity> {
        override fun createFromParcel(parcel: Parcel): ContactEntity {
            return ContactEntity(parcel)
        }

        override fun newArray(size: Int): Array<ContactEntity?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "ContactEntity( imageUri : $imageUri, name = $name, mobileNumber : $mobileNumber )"
    }
}