package id.refactory.androidmaterial.day21.repositories.local.daos

import androidx.room.*
import id.refactory.androidmaterial.day21.repositories.local.entities.TodoEntity

@Dao
interface TodoDao {
    @Query("SELECT * FROM tabel_todo ORDER BY ID DESC")
    suspend fun getAllTodo(): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todoEntity: TodoEntity)

    @Update
    suspend fun updateTodo(todoEntity: TodoEntity)

    @Delete
    suspend fun deleteTodo(todoEntity: TodoEntity)
}