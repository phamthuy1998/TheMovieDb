package com.thuypham.ptithcm.data.local

import android.content.Context
import com.google.gson.Gson

class SharedPreferencesStorage constructor(context: Context, private val gson: Gson) : IStorage {

    private val sharedPreferences = context.getSharedPreferences(
        APP_PREF,
        Context.MODE_PRIVATE
    )

    override fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getString(key: String, def: String?) = sharedPreferences.getString(key, def)

    override fun setBoolean(key: String, value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    override fun getBoolean(key: String, def: Boolean) = sharedPreferences.getBoolean(key, def)

    override fun setInt(key: String, value: Int) {
        with(sharedPreferences.edit()) {
            putInt(key, value)
            apply()
        }
    }

    override fun getInt(key: String, def: Int) = sharedPreferences.getInt(key, def)

    override fun setLong(key: String, value: Long) {
        with(sharedPreferences.edit()) {
            putLong(key, value)
            apply()
        }
    }

    override fun getLong(key: String, def: Long) = sharedPreferences.getLong(key, def)

    override fun setStringSet(key: String, set: Set<String>) {
        with(sharedPreferences.edit()) {
            putStringSet(key, set)
            apply()
        }
    }

    override fun getStringSet(key: String) =
        sharedPreferences.getStringSet(key, emptySet()) as Set<String>

    override fun setObject(key: String, obj: Any) {
        setString(key, gson.toJson(obj))
    }

    override fun getObject(key: String): Any? {
        val objString = sharedPreferences.getString(key, "")
        return try {
            gson.fromJson(objString, Any::class.java)
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        const val APP_PREF = "TheMovieDB"
        const val IS_FIRST_TIME_OPEN_APP = "IS_FIRST_TIME_OPEN_APP"
        const val IS_RECYCLERVIEW_LAYOUT_GRID_VIEW = "IS_RECYCLERVIEW_LAYOUT_GRID_VIEW"
    }
}

interface IStorage {
    fun setString(key: String, value: String)
    fun getString(key: String, def: String? = null): String?
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, def: Boolean): Boolean
    fun setInt(key: String, value: Int)
    fun getInt(key: String, def: Int = -1): Int
    fun setLong(key: String, value: Long)
    fun getLong(key: String, def: Long = -1L): Long
    fun setStringSet(key: String, set: Set<String>)
    fun getStringSet(key: String): Set<String>
    fun setObject(key: String, obj: Any)
    fun getObject(key: String): Any?
}