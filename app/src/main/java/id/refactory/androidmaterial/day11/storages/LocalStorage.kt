package id.refactory.androidmaterial.day11.storages

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class LocalStorage(private val context: Context) {

    companion object {
        private const val NAME = "id.refactory.androidmaterial"
        private const val KEY_TODO = "KEY_TODO"
    }

    private val sharedPreferences by lazy {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    private inline fun <reified T> SharedPreferences.save(key: String, value: T) {
        edit {
            when (value) {
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                is Long -> putLong(key, value)
                is Set<*> -> putStringSet(key, value as? Set<String> ?: setOf())
            }
        }
    }

    fun setTodo(data: Set<String>) {
        sharedPreferences.save(KEY_TODO, data)
    }

    fun addTodo(data: String) {
        val todo = getTodo().toMutableList().apply { add(data) }.toSet()
        sharedPreferences.save(KEY_TODO, todo)
    }

    fun getTodo(): Set<String> {
        return sharedPreferences.getStringSet(KEY_TODO, setOf()) ?: setOf()
    }
}