package id.refactory.androidmaterial.day17.repositories.local.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import id.refactory.androidmaterial.day17.repositories.local.entities.TodoEntity

class LocalDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "local.db"
        private const val DATABASE_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(TodoEntity.SQL_CREATE_TODO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        when (newVersion) {
            1 -> {
                db?.execSQL(TodoEntity.SQL_DELETE_TODO)
                db?.execSQL(TodoEntity.SQL_CREATE_TODO)
            }

            2 -> db?.execSQL(TodoEntity.SQL_MIGRATE_TODO)
        }
    }
}