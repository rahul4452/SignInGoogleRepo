package com.example.signingoogle.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

open class SaveData {

    private lateinit var _context: Context
    private var shared: SharedPreferences? = null
    private val SHARED_NAME = "Shelf"
    private var edit: SharedPreferences.Editor? = null


    constructor(_context: Context) {
        this._context = _context
        this.shared = _context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        this.edit = shared!!.edit()
    }


    // ============================================//
    fun save(key: String, value: String) {
        edit?.putString(key, value)
        edit?.commit()
    }


    // ============================================//
    fun save(key: String, value: Float) {
        edit?.putFloat(key, value)
        edit?.commit()
    }

    // ============================================//
    fun save(key: String, value: Boolean) {
        edit?.putBoolean(key, value)
        edit?.commit()
    }

    // ============================================//
    fun save(key: String, value: Int) {
        edit?.putInt(key, value)
        edit?.commit()
    }

    // ============================================//
    fun getString(key: String): String? {
        return shared!!.getString(key, key)

    }

    // ============================================//
    fun getInt(key: String): Int {
        return shared!!.getInt(key, 0)

    }

    // ============================================//
    fun getFloat(key: String): Float {
        return shared!!.getFloat(key, 0f)

    }

    // ============================================//
    fun getBoolean(key: String): Boolean {
        return shared!!.getBoolean(key, true)

    }

    // ============================================//
    fun isExist(key: String): Boolean {
        return shared!!.contains(key)

    }

    // ============================================//
    fun remove(key: String) {

        edit?.remove(key)
        edit?.commit()

    }

    // ============================================//
    operator fun get(key: String): String? {
        return shared!!.getString(key, key)

    }

    fun clearAll() {
        shared = _context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        shared!!.edit().clear().commit()

    }
}