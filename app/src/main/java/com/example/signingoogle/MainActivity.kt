package com.example.signingoogle

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.signingoogle.utilities.UseFull

import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*

import android.os.Build
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import com.example.signingoogle.taketest.Categories
import com.example.signingoogle.roomDatabase.QuizDatabase
import com.example.signingoogle.roomDatabase.User
import com.example.signingoogle.utilities.SaveData
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity(), View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    override fun onConnectionFailed(p0: ConnectionResult) {
//        Log.e("Tag", "onConnectionFailed():${ConnectionResult}.")
        Toast.makeText(applicationContext, "Google Play Services error.", Toast.LENGTH_SHORT).show()
    }


    private var RC_SIGN_IN = 1234

    val TAG = "CreateAccount"

    private val WEB_CLIENT_ID = "36291943671-6trj2igt77dhvvdvk378ipf9711i4i09.apps.googleusercontent.com"

    private var mGoogleApiClient: GoogleApiClient? = null

    private lateinit var quizDb: QuizDatabase

    private lateinit var auth: FirebaseAuth

    lateinit var obj_UsefullData: UseFull

    lateinit var _saveData: SaveData

//    //Facebook Callback manager
//    var callbackManager: CallbackManager? = null

    var colors = IntArray(2) { R.color.green;R.color.green }


    override fun onStart() {
        super.onStart()

        // ================= check If User Already Logged In ================= //

        if (auth.currentUser != null) {


            val intent2 = Intent(this, Categories::class.java)
            intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent2)
        }

    }


    @TargetApi(Build.VERSION_CODES.P)
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("PackageManagerGetSignatures")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initUI()
        setupUI()


    }

    private fun setupUI() {

        //=========== Setting Font Style =============//

        app_title.typeface = obj_UsefullData.get_Zamenhof_regular()
        take_quiz.typeface = obj_UsefullData.get_proxima_light()


        //============= Image Animation ============== //

        val animShake: Animation = AnimationUtils.loadAnimation(this, R.anim.animshake)
        logo.startAnimation(animShake)


        sign_in_button.setOnClickListener(this)

        take_quiz.setOnClickListener(this)
    }

    private fun initUI() {

        obj_UsefullData = object : UseFull(applicationContext) {}

        _saveData = object : SaveData(applicationContext) {}


        // ============= Intialize database ============== //

        quizDb = QuizDatabase.getInstance(applicationContext)!!


        // ============= Get Authentication Instance from ============== //
        auth = FirebaseAuth.getInstance()


        // =============== Pass WebClient Key To Google =============== //
        val userSign = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT_ID)
            .requestEmail()
            .build()


        // =============== Get SignIn Client ================== //
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this/* OnConnectionFailedListener */)
            .addApi(Auth.GOOGLE_SIGN_IN_API, userSign)
            .build()


    }


    override fun onClick(v: View?) {
        val i = v!!.id

        when (i) {

            R.id.sign_in_button -> {
                googleLogin()
            }

            R.id.take_quiz -> {

                if (auth.currentUser != null) {
                    val intent1 = Intent(this, Categories::class.java)
                    intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent1)
                } else {
                    obj_UsefullData.get_alertDialoug(this)
                }
            }
        }
    }


    // ================= Login Through Google ================= //
    private fun googleLogin() {

        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, RC_SIGN_IN)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == RC_SIGN_IN) {

            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            if (result.isSuccess) {
                firebaseAuthWithGoogle(result.signInAccount!!)
            }

        }

    }


    // =================== Firebase Authentication To Google =================== //
    private fun firebaseAuthWithGoogle(signInAccount: GoogleSignInAccount) {

        Log.i("TAG", "Authenticating user with firebase.")

        val credential = GoogleAuthProvider.getCredential(signInAccount.idToken, null)

        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->

            Log.i("TAG", "Firebase Authentication, is result a success? ${task.isSuccessful}.")

            if (task.isSuccessful) {

                loadUser()

            } else {

                // If sign in fails, display a message to the user.
                Log.e("TAG", "Authenticating with Google credentials in firebase FAILED !!")

            }
        }
    }

    private fun loadUser() {
        val loggedUser = auth.currentUser

        val user = User(
            user_id = null,
            user_email = loggedUser?.email,
            user_name = loggedUser?.displayName,
            user_profile_image = loggedUser?.photoUrl.toString()
        )

        doAsync {
            quizDb.quizUserInterface().insert(user)

            uiThread {
                sign_in_button.visibility = View.GONE

                var gd = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
                gd.gradientType = GradientDrawable.LINEAR_GRADIENT
                gd.cornerRadius = 16F
                take_quiz.background = gd
            }
        }
    }


}
