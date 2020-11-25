package id.refactory.androidmaterial.day18.models

import android.os.Parcelable
import id.refactory.androidmaterial.day18.repositories.local.entities.TodoEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoModel(val id: Long, var task: String, var status: Boolean = false) : Parcelable

fun TodoModel.toEntity() : TodoEntity = TodoEntity(id, task, status)