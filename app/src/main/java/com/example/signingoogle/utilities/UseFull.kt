package com.example.signingoogle.utilities

import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import androidx.appcompat.app.AlertDialog
import android.R
import android.widget.TextView
import com.androidadvance.topsnackbar.TSnackbar


open class UseFull {

    private lateinit var _context: Context

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


//    fun make_alert(msg: String, is_success_msg: Boolean) {
//        var msg = msg
//
//        if (msg.isEmpty()) {
//            msg = _context.resources.getString(R.string.wrong)
//        }
//
//        if (is_success_msg) {
//
//            val snackbar = TSnackbar.make(
//                InitializeActivity.current_activity.findViewById(android.R.id.content),
//                msg,
//                TSnackbar.LENGTH_LONG
//            )
//            val snackbarView = snackbar.getView()
//            snackbarView.setBackgroundColor(InitializeActivity.current_activity.getResources().getColor(R.color.success))
//            val textView = snackbarView.findViewById(R.id.snackbar_text) as TextView
//            textView.setTextColor(InitializeActivity.current_activity.getResources().getColor(R.color.white))
//            textView.typeface = get_montserrat_regular()
//            snackbar.show()
//
//        } else {
//
//            val snackbar = TSnackbar.make(
//                InitializeActivity.current_activity.findViewById(android.R.id.content),
//                msg,
//                TSnackbar.LENGTH_LONG
//            )
//            val snackbarView = snackbar.getView()
//            snackbarView.setBackgroundColor(InitializeActivity.current_activity.getResources().getColor(R.color.fail))
//            val textView = snackbarView.findViewById(R.id.snackbar_text) as TextView
//            textView.setTextColor(InitializeActivity.current_activity.getResources().getColor(R.color.white))
//            textView.typeface = get_montserrat_regular()
//            snackbar.show()
//        }
//    }


}