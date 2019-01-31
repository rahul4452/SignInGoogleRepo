package com.example.signingoogle.taketest

import android.os.AsyncTask
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
import java.util.*



class StartQuiz : AppCompatActivity(), View.OnClickListener {


    lateinit var obj_UsefullData: UseFull
    private lateinit var quizDb: QuizDatabase
    var activityName: Int = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.signingoogle.R.layout.activity_start_quiz)

        initUI()

        setupUI()


        // ======================= Spinner Drop down elements for Number Of Question =========================
        //showDialog()
        setupSpinner()

        // ======================= Spinner Drop down elements for Difficult Level=========================
        //setupSpinnerDifficult()
        difficultLevel.setOnClickListener(this)

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




    private fun setupSpinnerDifficult() {
        // ======================= Spinner Drop down elements for Difficulti Level=========================
//        val difficultList = ArrayList<String>()
//        difficultList.add("Easy")
//        difficultList.add("Medium")
//        difficultList.add("Hard")
//
//
//        // ================== Creating adapter for spinner =======================
//        val dataAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficultList)
//        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//        spinner.adapter = dataAdapter2
    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.difficultLevel -> {
                showDialog()
            }

        }
    }


    private fun setupSpinner() {

//        // ======================= Spinner Drop down elements for Number Of Question =========================
        val categories = ArrayList<Int>()
        categories.add(5)
        categories.add(10)
        categories.add(15)
        categories.add(20)


//        // ================== Creating adapter for spinner =======================
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = dataAdapter

    }

    private fun setupUI() {

        //Setting up the TypeFace

        question.typeface = obj_UsefullData.Aller_bd()
        bestScore.typeface = obj_UsefullData.Aller_bd()
        time.typeface = obj_UsefullData.Aller_bd()
        difficultLevel.typeface = obj_UsefullData.Aller_bd()






        /** ======= Setting Up Toolbar Title getting =======
         *=======  Category Name From Database ============== **/



        doAsync {


            var activityHeader: List<Category> = quizDb.quizCategoryInterface().getCategoryRow(activityName)

            uiThread {

                for (i in 0 until activityHeader.size) {
                    toolbar_title.typeface = obj_UsefullData.Aller_Rg()
                    toolbar_title.text = activityHeader[i].category_name!!

                }

            }
        }


    }


    private fun initUI() {

        obj_UsefullData = object : UseFull(applicationContext) {}
        quizDb = QuizDatabase.getInstance(applicationContext)!!

        var bundle: Bundle? = intent.extras

        if (bundle != null) {
            activityName = bundle.getInt("category_id")
        }

    }


}
