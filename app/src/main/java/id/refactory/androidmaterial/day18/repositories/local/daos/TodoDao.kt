package id.refactory.androidmaterial.day18.repositories.local.daos

import androidx.room.*
import id.refactory.androidmaterial.day18.repositories.local.entities.TodoEntity

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY ID DESC")
    fun getAllTodo(): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createTodo(entity: TodoEntity): Long

    @Update
    fun updateTodo(entity: TodoEntity)

    @Delete
    fun deleteTodo(entity: TodoEntity)
}