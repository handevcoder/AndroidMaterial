package id.refactory.androidmaterial.day18.repositories.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.refactory.androidmaterial.day18.models.TodoModel

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "task") var task: String = "",
    @ColumnInfo(name = "status") var status: Boolean = false,
)

fun TodoEntity.toModel() : TodoModel = TodoModel(id, task, status)