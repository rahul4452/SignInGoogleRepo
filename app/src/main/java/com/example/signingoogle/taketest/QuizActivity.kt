package com.example.signingoogle.taketest

import android.app.PendingIntent.getActivity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidadvance.topsnackbar.TSnackbar
import com.example.signingoogle.API.ApiClient
import com.example.signingoogle.API.ApiInterface
import com.example.signingoogle.R
import com.example.signingoogle.adapter.CustomItemClickListner
import com.example.signingoogle.adapter.QuestionAdapter
import com.example.signingoogle.pojo.QuestionResponse
import com.example.signingoogle.roomDatabase.QuizDatabase
import com.example.signingoogle.utilities.Modal
import com.example.signingoogle.utilities.UseFull
import kotlinx.android.synthetic.main.activity_quiz.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import java.util.concurrent.TimeUnit

import com.ankushgrover.hourglass.Hourglass





class QuizActivity : AppCompatActivity() {


    private lateinit var objeUseFull: UseFull
    private lateinit var quizDb: QuizDatabase
    private var service: ApiInterface? = null

    private lateinit var modal: Modal
    private val handler = Handler()
    private var quesNumber: String? = null

    var isRunning = false
    private lateinit var alertDialog: AlertDialog
    private var levelOfDifficulty: String? = null

    private var timeForQuiz: String? = null

    private var categoryQuiz: String? = null

    private var questionList: ArrayList<Modal> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        initUIQuizActivity()

        getBundledata()

        doAsync {

            var cateName = quizDb.quizCategoryInterface().getCategoryCode(categoryQuiz)

            uiThread {
                for (i in 0 until cateName.size) {
                    cateCode = cateName[i].category_code
                }


                setupUIQuizActivity()

                callApiForQuetion()

                timeforQuiz()
            }


        }

    }

    private lateinit var hourglass: Hourglass

    private fun timeforQuiz() {

        val numberOnly = timeForQuiz!!.replace("[^0-9]".toRegex(), "")

        val time: Long = numberOnly?.toLong()

        val mili: Long = TimeUnit.MINUTES.toMillis(time)

        var pStatus = time


         hourglass = object : Hourglass(mili, 1000) {
            override fun onTimerTick(millisUntilFinished: Long) {
                this@QuizActivity.isRunning = true
                if(!alertDialog.isShowing){
                    hourglass.resumeTimer()
                }

                val minutes = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000 % 60)



                timeRemaining.text = String.format("%02d:%02d", minutes, seconds)

                Thread(Runnable {

                    while (pStatus > 0) {
                        pStatus -= (millisUntilFinished / 1000).toInt()

                        handler.post {

                            progressBar2.progress = pStatus.toInt()

                            progressBar2.progress = pStatus.toInt()

                        }
                        try {
                            // Sleep for 200 milliseconds.
                            // Just to display the progress slowly
                            Thread.sleep(200)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                    }

                }).start()

            }

            override fun onTimerFinish() {
                this@QuizActivity.isRunning = false


            }
        }


//        var timer = object : CountDownTimer(mili, 1000) {
//
//            override fun onTick(millisUntilFinished: Long) {
//
////                isRunning = true
////                val minutes = millisUntilFinished / 1000 / 60
////                val seconds = (millisUntilFinished / 1000 % 60)
////
////
////
////                timeRemaining.text = String.format("%02d:%02d", minutes, seconds)
////
////                Thread(Runnable {
////
////                    while (pStatus > 0) {
////                        pStatus -= (millisUntilFinished / 1000).toInt()
////
////                        handler.post {
////
////                            progressBar2.progress = pStatus.toInt()
////
////                            progressBar2.progress = pStatus.toInt()
////
////                        }
////                        try {
////                            // Sleep for 200 milliseconds.
////                            // Just to display the progress slowly
////                            Thread.sleep(200)
////                        } catch (e: InterruptedException) {
////                            e.printStackTrace()
////                        }
////
////                    }
////
////                }).start()
//
//            }
//
//            override fun onFinish() {
////                isRunning = false
//            }
//        }
//        timer.start()

        hourglass.startTimer()
    }


    override fun onBackPressed() {

        if (!isRunning) {
            super.onBackPressed()
        } else {



            hourglass.pauseTimer()

            val viewGroup = findViewById<ViewGroup>(android.R.id.content)

            //then we will inflate the custom alert dialog xml that we created
            val dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, viewGroup, false)

            //Now we need an AlertDialog.Builder object
            val builder = AlertDialog.Builder(this)

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView)

            val dialogButton = dialogView.findViewById(R.id.buttonOk) as Button

            val dialogCancalButton = dialogView.findViewById(R.id.buttonCancal) as Button

            dialogButton.setOnClickListener {
                if (alertDialog.isShowing) {
                    alertDialog.dismiss()
                    hourglass.resumeTimer()
                }
            }

            dialogCancalButton.setOnClickListener {
                if (alertDialog.isShowing) {
                    alertDialog.dismiss()
                    hourglass.stopTimer()
                    finish()
                }
            }

            //finally creating the alert dialog and displaying it
            alertDialog = builder.create()
            alertDialog.show()



        }

    }


    private var cateCode: Int? = null

    private fun getBundledata() {
        var bundle: Bundle? = intent.extras

        if (bundle != null) {
            quesNumber = bundle.getString("question")
            levelOfDifficulty = bundle.getString("difficulty")
            timeForQuiz = bundle.getString("time")
            categoryQuiz = bundle.getString("category")
        }


    }

    private var linearLayoutManager: LinearLayoutManager? = null

    private fun callApiForQuetion() {

        val responscall: Call<QuestionResponse> = //service!!.getQuestionList(quesNumber)
            service!!.getQuestionList(quesNumber, cateCode, levelOfDifficulty, "multiple")

        responscall.enqueue(object : Callback<QuestionResponse> {


            override fun onResponse(call: Call<QuestionResponse>, response: Response<QuestionResponse>) {

                if (response.isSuccessful) {

                    response.code()

                    var question: List<QuestionResponse.Result> = response.body()?.getResults()!!

                    for (question: QuestionResponse.Result in question.listIterator()) {

                        modal = Modal(
                            question.category,
                            question.difficulty,
                            question.question,
                            question.correctAnswer,
                            question.incorrectAnswers
                        )

                        questionList.add(modal)

                    }

                    var question_adapter = QuestionAdapter(questionList, this@QuizActivity, recyclerView)

                    recyclerView.adapter = question_adapter


                    linearLayoutManager =
                            object : LinearLayoutManager(this@QuizActivity, LinearLayoutManager.HORIZONTAL, false) {

                                override fun canScrollHorizontally(): Boolean {
                                    return false
                                }
                            }

                    //Add a LayoutManager
                    recyclerView.layoutManager = linearLayoutManager

                    question_adapter.setOnItemClickListener(object : CustomItemClickListner {
                        override fun onCustomItemClickListener(view: View, pos: Int) {

                            Toast.makeText(applicationContext, "inside activity", Toast.LENGTH_SHORT).show()

                            recyclerView.smoothScrollToPosition(pos + 1)

                        }

                    })

                } else {
                    val snackbar = TSnackbar.make(
                        findViewById<View>(android.R.id.content),
                        "Response UnSuccessful",
                        TSnackbar.LENGTH_LONG
                    )
                    snackbar.setActionTextColor(Color.WHITE)
                    val snackbarView = snackbar.view
                    snackbarView.setBackgroundColor(Color.parseColor("#CC00CC"))
                    val textView =
                        snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
                    textView.setTextColor(Color.YELLOW)
                    snackbar.show()
                }
            }

            override fun onFailure(call: Call<QuestionResponse>, t: Throwable) {

                val snackbar = TSnackbar.make(
                    findViewById<View>(android.R.id.content),
                    "error Found",
                    TSnackbar.LENGTH_LONG
                )
                snackbar.setActionTextColor(Color.WHITE)
                val snackbarView = snackbar.view
                snackbarView.setBackgroundColor(Color.parseColor("#CC00CC"))
                val textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
                textView.setTextColor(Color.YELLOW)
                snackbar.show()


            }

        })

    }


    private fun setupUIQuizActivity() {

        saveLifeLine.typeface = objeUseFull.get_Zamenhof_regular()
        life.typeface = objeUseFull.Aller_Rg()
        score.typeface = objeUseFull.Aller_Rg()
        showScore.typeface = objeUseFull.get_Zamenhof_regular()


        life1.typeface = objeUseFull.Aller_Rg()
        life2.typeface = objeUseFull.Aller_Rg()
        life3.typeface = objeUseFull.Aller_Rg()

    }


    private fun initUIQuizActivity() {

        objeUseFull = object : UseFull(applicationContext) {}
        quizDb = QuizDatabase.getInstance(applicationContext)!!

        service = ApiClient().getClient()?.create(ApiInterface::class.java)

    }
}
