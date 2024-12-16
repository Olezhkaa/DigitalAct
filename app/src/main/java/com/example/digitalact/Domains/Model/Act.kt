package com.example.digitalact.Domains.Model

import android.os.Parcel
import android.os.Parcelable

data class Act (
    val accountNumber: String,
    val address: String,
    val fullName: String,
    val typeOldPU: String,
    val numberOldPU: String,
    val typeOldTT: String,
    val numberOldTT: String,
    val reasonReplacement: String,
    val numberApplication: String,
    val typeNewPU: String,
    val numberNewPU: String,
    val typeNewTT: String,
    val numberNewTT: String,
    val sealPUOne: String,
    val sealPUTwo: String,
    val sealPUThree: String,
    val simCard: String,
    val dateCompletion: String,
    val fullNameExecutor: String,
    val installationLocation: String,
    val comment: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    constructor() :this("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(accountNumber)
        parcel.writeString(address)
        parcel.writeString(fullName)
        parcel.writeString(typeOldPU)
        parcel.writeString(numberOldPU)
        parcel.writeString(typeOldTT)
        parcel.writeString(numberOldTT)
        parcel.writeString(reasonReplacement)
        parcel.writeString(numberApplication)
        parcel.writeString(typeNewPU)
        parcel.writeString(numberNewPU)
        parcel.writeString(typeNewTT)
        parcel.writeString(numberNewTT)
        parcel.writeString(sealPUOne)
        parcel.writeString(sealPUTwo)
        parcel.writeString(sealPUThree)
        parcel.writeString(simCard)
        parcel.writeString(dateCompletion)
        parcel.writeString(fullNameExecutor)
        parcel.writeString(installationLocation)
        parcel.writeString(comment)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Act> {
        override fun createFromParcel(parcel: Parcel): Act {
            return Act(parcel)
        }

        override fun newArray(size: Int): Array<Act?> {
            return arrayOfNulls(size)
        }
    }
}