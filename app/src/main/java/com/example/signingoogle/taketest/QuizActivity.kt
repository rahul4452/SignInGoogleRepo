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
import androidx.recyclerview.widget.RecyclerView
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

class QuizActivity : AppCompatActivity() {


    private lateinit var objeUseFull: UseFull
    private lateinit var quizDb: QuizDatabase
    private var service: ApiInterface? = null
    var pStatus = 0
    private lateinit var modal: Modal
    private val handler = Handler()
    private var quesNumber: String? = null


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


//        Thread(Runnable {
//                // TODO Auto-generated method stub
//            while (pStatus < 100) {
//                pStatus += 1
//
//                handler.post {
//                    // TODO Auto-generated method stub
//                    progressBar2.progress = pStatus
//
//                    progressBar2.progress = pStatus
//
//                }
//                try {
//                    // Sleep for 200 milliseconds.
//                    // Just to display the progress slowly
//                    Thread.sleep(200)
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//
//            }
//        }).start()
    }

    private fun timeforQuiz() {

        val time : Long = timeForQuiz?.toLong() ?: -1



        var timer = object: CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining.text = "seconds remaining: " + millisUntilFinished / 1000
            }

            override fun onFinish() {

            }
        }

        timer.start()
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
