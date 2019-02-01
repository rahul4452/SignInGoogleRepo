package com.example.signingoogle.taketest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidadvance.topsnackbar.TSnackbar
import com.example.signingoogle.API.ApiClient
import com.example.signingoogle.API.ApiInterface
import com.example.signingoogle.R
import com.example.signingoogle.adapter.QuestionAdapter
import com.example.signingoogle.pojo.QuestionResponse
import com.example.signingoogle.roomDatabase.QuizDatabase
import com.example.signingoogle.utilities.Modal
import com.example.signingoogle.utilities.UseFull
import kotlinx.android.synthetic.main.activity_quiz.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizActivity : AppCompatActivity() {



    private  lateinit var objeUseFull: UseFull
    private lateinit var quizDb: QuizDatabase
    private var service: ApiInterface? = null
    var pStatus = 0
    private lateinit var modal: Modal
    private val handler = Handler()
    private var quesNumber: String? = null



    private var levelOfDifficulty: String? = null

    private var timeForQuiz: String? = null

    private var categoryQuiz: String? = null

   private lateinit var questionList : ArrayList<Modal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        initUIQuizActivity()

        getBundledata()

        setupUIQuizActivity()

        callApiForQuetion()

        setupRecycleQuizActivity()

        Thread(Runnable {
                // TODO Auto-generated method stub
            while (pStatus < 100) {
                pStatus += 1

                handler.post {
                    // TODO Auto-generated method stub
                    progressBar2.progress = pStatus

                    progressBar2.progress = pStatus

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



    private fun getBundledata() {
        var bundle : Bundle ? = intent.extras

        if(bundle!=null){
           quesNumber =  bundle.getString("question")
           levelOfDifficulty =  bundle.getString("difficulty")
           timeForQuiz =  bundle.getString("time")
           categoryQuiz =  bundle.getString("category")
        }
    }

    private fun callApiForQuetion() {

        val responscall : Call<QuestionResponse> = service!!.getQuestionList(quesNumber,categoryQuiz,levelOfDifficulty,"multiple")

        responscall.enqueue(object : Callback<QuestionResponse>{


            override fun onResponse(call: Call<QuestionResponse>, response: Response<QuestionResponse>) {

                if(response.isSuccessful){

                    var question = response.body()?.getResults()

                    for(question:QuestionResponse.Result in question!!.listIterator()){

                        modal = Modal(question.category,question.difficulty,question.question,question.correctAnswer,question.incorrectAnswers)

                        questionList.add(modal)

                    }

                    var question_adapter = QuestionAdapter(questionList, this@QuizActivity)

                    recyclerView.adapter = question_adapter

                    //Add a LayoutManager
                    recyclerView.layoutManager = LinearLayoutManager(this@QuizActivity, RecyclerView.HORIZONTAL, false)

                }
                else{
                    val snackbar = TSnackbar.make(
                        findViewById<View>(android.R.id.content),
                        "Response UnSuccessful",
                        TSnackbar.LENGTH_LONG
                    )
                    snackbar.setActionTextColor(Color.WHITE)
                    val snackbarView = snackbar.view
                    snackbarView.setBackgroundColor(Color.parseColor("#CC00CC"))
                    val textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
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

    private fun setupRecycleQuizActivity() {

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

        objeUseFull = object : UseFull(applicationContext){}
        quizDb = QuizDatabase.getInstance(applicationContext)!!

        service = ApiClient().getClient()?.create(ApiInterface::class.java)

    }
}
