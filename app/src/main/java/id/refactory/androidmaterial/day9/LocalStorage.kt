package id.refactory.androidmaterial.day9

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class LocalStorage(private val context: Context) {

    companion object {
        private const val NAME = "id.refactory.androidmaterial"
        private const val KEY_NAME = "KEY_NAME"
        private const val KEY_AGE = "KEY_AGE"
        private const val KEY_WEIGHT = "KEY_WEIGHT"
        private const val KEY_IS_SINGLE = "KEY_IS_SINGLE"
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

    fun setName(name: String) {
        sharedPreferences.save(KEY_NAME, name)
    }

    fun getName(): String {
        return sharedPreferences.getString(KEY_NAME, "") ?: ""
    }

    fun setAge(age: Int) {
        sharedPreferences.save(KEY_AGE, age)
    }

    fun getAge(): Int {
        return sharedPreferences.getInt(KEY_AGE, -1)
    }

    fun setWeight(weight: Float) {
        sharedPreferences.save(KEY_WEIGHT, weight)
    }

    fun getWeight(): Float {
        return sharedPreferences.getFloat(KEY_WEIGHT, -1.0f)
    }

    fun setIsSingle(single: Boolean) {
        sharedPreferences.save(KEY_IS_SINGLE, single)
    }

    fun getIsSingle(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_SINGLE, true)
    }
}