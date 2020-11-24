package id.refactory.androidmaterial.day17.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoModel(val id: Long, var task: String, var status: Boolean = false) : Parcelable