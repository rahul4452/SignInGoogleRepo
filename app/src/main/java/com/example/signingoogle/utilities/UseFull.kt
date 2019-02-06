package com.example.signingoogle.utilities

import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import androidx.appcompat.app.AlertDialog
import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup






open class UseFull {

    private var _context: Context

    // =============== Constructor ===================  //
    constructor(_context: Context) {
        this._context = _context
    }



    // ===================== Set Font ===================== //
    fun get_Zamenhof_regular(): Typeface {
        return Typeface.createFromAsset(
            _context.assets,
            "Zamenhof Inline.ttf"
        )
    }

    fun get_proxima_light(): Typeface {
        return Typeface.createFromAsset(
            _context.assets,
            "ProximaNova-Light.otf"
        )
    }


    fun Aller_bd(): Typeface {
        return Typeface.createFromAsset(
            _context.assets,
            "Aller_Bd.ttf"
        )
    }

    fun Aller_Rg(): Typeface {
        return Typeface.createFromAsset(
            _context.assets,
            "Aller_Rg.ttf"
        )
    }

    // =================== INTERNET ===================//
    fun isNetworkConnected(): Boolean {
        val cm = _context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        return ni != null
    }

    fun get_alertDialoug(_context: Context) {
        val dlgAlert = AlertDialog.Builder(_context)

        dlgAlert.setMessage("You Must Login First")
        dlgAlert.setTitle("Login Error ...")
        dlgAlert.setCancelable(true)
        dlgAlert.create().show()

    }

}