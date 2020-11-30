package id.refactory.androidmaterial.day21.models

import android.os.Parcelable
import id.refactory.androidmaterial.day21.repositories.local.entities.TodoEntity
import id.refactory.androidmaterial.day21.repositories.remote.requests.TodoRequest
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoModel(
    val id: Long = 0,
    var task: String = "",
    var status: Boolean = false,
    var date: String = ""
): Parcelable

fun TodoModel.toEntity() = TodoEntity(id, task, status, date)
fun TodoModel.toRequest() = TodoRequest(status, task, date)