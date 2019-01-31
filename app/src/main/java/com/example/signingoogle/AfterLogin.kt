package com.example.signingoogle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_after_login.*
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import org.json.JSONObject





class AfterLogin : AppCompatActivity(), View.OnClickListener {


    private var firebaseAuth: FirebaseAuth? = null
    var response: JSONObject? = null
    var profile_pic_data:JSONObject? = null
    var profile_pic_url:JSONObject? = null

    private var mGoogleApiClient: GoogleApiClient? = null

    private val WEB_CLIENT_ID = "36291943671-6trj2igt77dhvvdvk378ipf9711i4i09.apps.googleusercontent.com"

    private var jsondata: String?  = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_login)

        firebaseAuth = FirebaseAuth.getInstance()

        val user = firebaseAuth?.currentUser

        if(user!= null){
            Glide.with(this)
                .load(user.photoUrl)
                .into(profile_image)


            emailtv.text = user.email


            usernameTV.text = user.displayName

        }

        sign_out_button.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        val i = v!!.id

        when(i){
            R.id.sign_out_button ->{
                googleSignOut()
            }
        }
    }

    private fun googleSignOut() {
        var mGoogleSignInClient : GoogleSignInClient

        val userSign  =  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT_ID)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, userSign)


        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                // ...
                // sign out Firebase
                firebaseAuth!!.signOut()

                //sign out Google



                    Toast.makeText(applicationContext, "Logged Out", Toast.LENGTH_SHORT).show()
                    val i = Intent(applicationContext, MainActivity::class.java)
                    startActivity(i)
                finish()

            }
    }




    override fun onStart() {


        super.onStart()
        if (firebaseAuth?.currentUser == null){

            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        val intent = intent
         jsondata = intent.getStringExtra("userProfile")
    }
}
