package id.refactory.androidmaterial.day17.repositories.local

import android.content.ContentValues
import android.content.Context
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import id.refactory.androidmaterial.day17.models.TodoModel
import id.refactory.androidmaterial.day17.repositories.TodoRepository
import id.refactory.androidmaterial.day17.repositories.local.databases.LocalDatabase
import id.refactory.androidmaterial.day17.repositories.local.entities.TodoEntity

class TodoLocalRepository(private val localDatabase: LocalDatabase) : TodoRepository {

    override fun getAllTodo(): List<TodoModel> {
        val db = localDatabase.readableDatabase
        val order = "${TodoEntity.COLUMN_ID} DESC"
        val cursor = db.query(
            TodoEntity.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            order
        )
        val todo = mutableListOf<TodoModel>()

        while (cursor.moveToNext()) {
            val id = cursor.getLongOrNull(cursor.getColumnIndexOrThrow(TodoEntity.COLUMN_ID))
            val name = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(TodoEntity.COLUMN_TASK))
            val status = cursor.getIntOrNull(cursor.getColumnIndexOrThrow(TodoEntity.COLUMN_STATUS)) ?: 0

            if (id != null && name != null) {
                val todoModel = TodoModel(id, name, status == 1)

                todo.add(todoModel)
            }
        }

        cursor.close()

        return todo
    }

    override fun updateTodo(todoModel: TodoModel): TodoModel {
        val db = localDatabase.writableDatabase
        val values = ContentValues().apply {
            put(TodoEntity.COLUMN_TASK, todoModel.task)
            put(TodoEntity.COLUMN_STATUS, if (todoModel.status) 1 else 0)
        }
        val selection = "${TodoEntity.COLUMN_ID} = ?"
        val selectionArgs = arrayOf("${todoModel.id}")

        db.update(TodoEntity.TABLE_NAME, values, selection, selectionArgs)
        db.close()

        return todoModel
    }

    override fun insertTodo(task: String): TodoModel {
        val db = localDatabase.writableDatabase
        val values = ContentValues().apply {
            put(TodoEntity.COLUMN_TASK, task)
            put(TodoEntity.COLUMN_STATUS, 0)
        }

        val id = db.insert(TodoEntity.TABLE_NAME, TodoEntity.COLUMN_ID, values)
        db.close()

        return TodoModel(id, task)
    }

    override fun deleteTodo(id: Long): Long {
        val db = localDatabase.writableDatabase
        val selection = "${TodoEntity.COLUMN_ID} = ?"
        val selectionArgs = arrayOf("$id")

        db.delete(TodoEntity.TABLE_NAME, selection, selectionArgs)
        db.close()

        return id
    }
}