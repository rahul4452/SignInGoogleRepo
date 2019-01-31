package com.example.signingoogle.utilities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import com.example.signingoogle.R
import com.example.signingoogle.MainActivity
import android.content.Intent
import com.example.signingoogle.AfterLogin
import com.google.firebase.auth.FirebaseAuth


class BaseActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null

    var handler: Handler? = null
    lateinit var obj_UsefullData: UseFull

    lateinit var _saveData: SaveData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)

        obj_UsefullData = object : UseFull(applicationContext) {}

        _saveData = object : SaveData(applicationContext) {}


        firebaseAuth = FirebaseAuth.getInstance()


        handler = Handler()
        handler!!.postDelayed({

            if(firebaseAuth?.currentUser != null) {
                val intent = Intent(this@BaseActivity, AfterLogin::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this@BaseActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }



}
