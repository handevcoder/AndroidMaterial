package id.refactory.androidmaterial.day18.repositories.local.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.refactory.androidmaterial.day18.repositories.local.daos.TodoDao
import id.refactory.androidmaterial.day18.repositories.local.entities.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun dao(): TodoDao

    companion object {
        @Volatile
        private lateinit var localDatabase: LocalDatabase

        private const val DATABASE_NAME = "local_todo.db"

        fun getDatabase(context: Context): LocalDatabase {
            if (!this::localDatabase.isInitialized) {
                localDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }

            return localDatabase
        }
    }
}