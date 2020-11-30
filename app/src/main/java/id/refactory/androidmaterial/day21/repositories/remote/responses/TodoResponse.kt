package id.refactory.androidmaterial.day21.repositories.remote.responses

import com.google.gson.annotations.SerializedName
import id.refactory.androidmaterial.day21.models.TodoModel

data class TodoResponse(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("task") val task: String = "",
    @SerializedName("status") val status: Boolean = false,
    @SerializedName("date") val date: String
)

fun TodoResponse.toModel() = TodoModel(id, task, status, date)