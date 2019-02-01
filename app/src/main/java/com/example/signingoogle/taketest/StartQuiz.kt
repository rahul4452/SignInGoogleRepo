package com.example.signingoogle.taketest


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.signingoogle.R
import com.example.signingoogle.roomDatabase.Category
import com.example.signingoogle.roomDatabase.QuizDatabase
import com.example.signingoogle.utilities.UseFull
import kotlinx.android.synthetic.main.activity_start_quiz.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.jetbrains.anko.doAsync

import org.jetbrains.anko.uiThread

import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_quiz.*
import java.util.*
import java.util.concurrent.TimeUnit

import android.os.Handler


class StartQuiz : AppCompatActivity(), View.OnClickListener {

    lateinit var obj_UsefullData: UseFull
    private lateinit var quizDb: QuizDatabase
    private var activityId: Int = 0
    private lateinit var activityName: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_quiz)

        initUI()
        setupUI()
        // ======================= Spinner Drop down elements for Number Of Question =========================
        setupSpinner()
        // ======================= Dialof for Difficult Level=========================
        difficultLevel.setOnClickListener(this)

        setTimeForQuiz()



    }

    private fun initUI() {

        obj_UsefullData = object : UseFull(applicationContext) {}
        quizDb = QuizDatabase.getInstance(applicationContext)!!

        var bundle: Bundle? = intent.extras

        if (bundle != null) {
            activityId = bundle.getInt("category_id")
            if(activityId==0){
                activityName = bundle.getString("categoryName")
            }

        }

        playnow.setOnClickListener(this)




    }

    private fun setupUI() {

        //Setting up the TypeFace

        question.typeface = obj_UsefullData.Aller_bd()
        bestScore.typeface = obj_UsefullData.Aller_bd()
        time.typeface = obj_UsefullData.Aller_bd()
        difficultLevel.typeface = obj_UsefullData.Aller_bd()






        /** ======= Setting Up Toolbar Title getting =======
         *=======  Category Name From Database ============== **/

        if(activityId==0) {

            toolbar_title.typeface = obj_UsefullData.Aller_Rg()
            toolbar_title.text = activityName

        }else{
            doAsync {


                val activityHeader: List<Category> = quizDb.quizCategoryInterface().getCategoryRow(activityId)

                uiThread {

                    for (i in 0 until activityHeader.size) {

                        toolbar_title.typeface = obj_UsefullData.Aller_Rg()
                        toolbar_title.text = activityHeader[i].category_name!!

                    }

                }
            }
        }

    }


    private fun setupSpinner() {

        // ======================= Spinner Drop down elements for Number Of Question ========================= //
        val categories = ArrayList<Int>()
        categories.add(5)
        categories.add(10)
        categories.add(15)
        categories.add(20)


//        // ================== Creating adapter for spinner =======================
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = dataAdapter


        /** ================== Setting Up time for Quiz ================= **/

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            @SuppressLint("SetTextI18n")
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                var quizLevel : String = difficultLevel.text.toString()

                if(quizLevel.equals("Easy")){

                    val seconds:Long = (categories[position] * 40).toLong()

                    val minutes = TimeUnit.SECONDS.toMinutes(seconds)

                    if(minutes > 60 ){
                        val hours : Long = TimeUnit.MINUTES.toHours(minutes)
                        timeQuiz.text = hours.toString() + "h"
                    }else{
                        timeQuiz.text = minutes.toString() + "m"
                    }

                }else if(quizLevel.equals("Medium")){

                    val seconds:Long = (categories[position] * 60).toLong()

                    val minutes = TimeUnit.SECONDS.toMinutes(seconds)

                    if(minutes > 60 ){
                        val hours : Long = TimeUnit.MINUTES.toHours(minutes)
                        timeQuiz.text = hours.toString() + "h"
                    }else{
                        timeQuiz.text = minutes.toString() + "m"
                    }

                }else if(quizLevel.equals("Hard")){

                    val seconds:Long = (categories[position] * 90).toLong()

                    val minutes = TimeUnit.SECONDS.toMinutes(seconds)

                    if(minutes > 60 ){
                        val hours : Long = TimeUnit.MINUTES.toHours(minutes)
                        timeQuiz.text = hours.toString() + " h"
                    }else{
                        timeQuiz.text = minutes.toString() + " m"
                    }

                }


            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTimeForQuiz() {
        var quizLevel : String = difficultLevel.text.toString()

        var quizQues : String = spinner.selectedItem.toString()



        if(quizLevel.equals("Easy")){

            val seconds:Long = (quizQues.toInt() * 40).toLong()

            val minutes = TimeUnit.SECONDS.toMinutes(seconds)

            if(minutes > 60 ){
                val hours : Long = TimeUnit.MINUTES.toHours(minutes)
                timeQuiz.text = hours.toString() + "h"
            }else{
                timeQuiz.text = minutes.toString() + "m"
            }

        }else if(quizLevel.equals("Medium")){

            val seconds:Long = (quizQues.toInt() * 60).toLong()

            val minutes = TimeUnit.SECONDS.toMinutes(seconds)

            if(minutes > 60 ){
                val hours : Long = TimeUnit.MINUTES.toHours(minutes)
                timeQuiz.text = hours.toString() + "h"
            }else{
                timeQuiz.text = minutes.toString() + "m"
            }

        }else if(quizLevel.equals("Hard")){

            val seconds:Long = (quizQues.toInt() * 90).toLong()

            val minutes = TimeUnit.SECONDS.toMinutes(seconds)

            if(minutes > 60 ){
                val hours : Long = TimeUnit.MINUTES.toHours(minutes)
                timeQuiz.text = hours.toString() + "h"
            }else{
                timeQuiz.text = minutes.toString() + "m"
            }

        }
    }

    private fun showDialog(){
        // Late initialize an alert dialog object
        lateinit var dialog:AlertDialog

        // Initialize an array of colors
        val array = arrayOf("Easy","Medium","Hard")

        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Select Difficulty Level")



        // Set the single choice items for alert dialog with initial selection
        builder.setSingleChoiceItems(array,-1) { _, which->

            // Get the dialog selected item
            val level = array[which]

            try {

                difficultLevel.text = level
                var quizQues : String = spinner.selectedItem.toString()



                if(level.equals("Easy")){

                    val seconds:Long = (quizQues.toInt() * 40).toLong()

                    val minutes = TimeUnit.SECONDS.toMinutes(seconds)

                    if(minutes > 60 ){
                        val hours : Long = TimeUnit.MINUTES.toHours(minutes)
                        timeQuiz.text = hours.toString() + "h"
                    }else{
                        timeQuiz.text = minutes.toString() + "m"
                    }

                }else if(level.equals("Medium")){

                    val seconds:Long = (quizQues.toInt() * 60).toLong()

                    val minutes = TimeUnit.SECONDS.toMinutes(seconds)

                    if(minutes > 60 ){
                        val hours : Long = TimeUnit.MINUTES.toHours(minutes)
                        timeQuiz.text = hours.toString() + "h"
                    }else{
                        timeQuiz.text = minutes.toString() + "m"
                    }

                }else if(level.equals("Hard")){

                    val seconds:Long = (quizQues.toInt() * 90).toLong()

                    val minutes = TimeUnit.SECONDS.toMinutes(seconds)

                    if(minutes > 60 ){
                        val hours : Long = TimeUnit.MINUTES.toHours(minutes)
                        timeQuiz.text = hours.toString() + " h"
                    }else{
                        timeQuiz.text = minutes.toString() + " m"
                    }

                }




            }catch (e:IllegalArgumentException){

                e.printStackTrace()

            }

            // Dismiss the dialog
            dialog.dismiss()
        }


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }


    override fun onClick(v: View?) {

        when(v?.id){

            R.id.difficultLevel -> {
                showDialog()
            }

            R.id.playnow ->{
                val quizActivity = Intent(this@StartQuiz, QuizActivity::class.java)
                quizActivity.putExtra("question",spinner.selectedItem.toString())
                quizActivity.putExtra("difficulty",difficultLevel.text.toString())
                quizActivity.putExtra("time",timeQuiz.text.toString())
                quizActivity.putExtra("category",toolbar_title.text.toString())
                startActivity(quizActivity)
            }


        }
    }










}
