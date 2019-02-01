package com.example.signingoogle.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.signingoogle.R
import com.example.signingoogle.utilities.Modal
import com.example.signingoogle.utilities.UseFull
import kotlinx.android.synthetic.main.quiz_recycleview.view.*
import kotlin.random.Random

class QuestionAdapter(questionAdapt: ArrayList<Modal>, val context: Context) :  RecyclerView.Adapter<QuestionHolder>() ,CompoundButton.OnCheckedChangeListener {
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

    }

    var quesList: ArrayList<Modal> = questionAdapt
    var ctx: Context = context
    private lateinit var obj_UsefullData: UseFull
    private lateinit var answerList : ArrayList<String>
    lateinit var random: java.util.Random

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionHolder {
        return QuestionHolder(LayoutInflater.from(ctx).inflate(R.layout.quiz_recycleview, parent, false))
    }

    override fun getItemCount(): Int {
        return quesList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuestionHolder, position: Int) {

        obj_UsefullData = object : UseFull(ctx) {}

        holder.showNumberOfQuestion.typeface = obj_UsefullData.get_Zamenhof_regular()
        holder.showNumberOfQuestion.text = "Ques"+position+"/"+quesList.size


        holder.showQuestion.typeface = obj_UsefullData.Aller_Rg()
        holder.showQuestion.text = quesList[position].question


        var incorrect = quesList[position].incorrectAnswers
        var corr = quesList[position].correctAnswer
        for(item : String in incorrect!!.listIterator()){

            answerList.add(item)

        }
        answerList.add(corr!!)

        random = java.util.Random()

        do {

            val option = answerList[random.nextInt(answerList.size)]


            when {

                holder.option1.text == "" -> holder.option1.text = option
                holder.option2.text == "" -> holder.option2.text = option
                holder.option3.text == "" -> holder.option3.text = option
                else -> holder.option4.text = option

            }

            answerList.remove(answerList.contains(option).toString())

        }while (answerList.size != 0)


    }


}

class QuestionHolder(view: View) : RecyclerView.ViewHolder(view) {

    // Holds the TextView that will add question
    val showNumberOfQuestion =view.numofQues
    val showQuestion = view.question


    val option1 = view.radio1
    val option2 = view.radio2
    val option3 = view.radio3
    val option4 = view.radio4

}
