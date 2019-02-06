package com.example.signingoogle.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.signingoogle.utilities.Modal
import com.example.signingoogle.utilities.UseFull
import kotlinx.android.synthetic.main.quiz_recycleview.view.*
import java.util.*
import androidx.core.text.HtmlCompat
import com.example.signingoogle.R



class QuestionAdapter(questionAdapt: ArrayList<Modal>, val context: Context, recyclerView: RecyclerView) : RecyclerView.Adapter<QuestionAdapter.QuestionHolder>() {


    private var lastCheckedRB: RadioButton? = null
    var quesList: ArrayList<Modal> = questionAdapt
    var ctx: Context = context
    private lateinit var obj_UsefullData: UseFull
    private var answerList: ArrayList<String> = ArrayList()
    var recyclerView: RecyclerView = recyclerView

    private  var mListener: CustomItemClickListner? = null

    fun setOnItemClickListener(mliasstener :CustomItemClickListner){
        this.mListener = mliasstener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionHolder {

        val itemView: View = LayoutInflater.from(ctx).inflate(R.layout.quiz_recycleview, parent, false)

        return QuestionHolder(itemView)
    }

    override fun getItemCount(): Int {
        return quesList.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuestionHolder, position: Int) {

        obj_UsefullData = object : UseFull(ctx) {}

        var quesNumber = position + 1

        holder.showNumberOfQuestion.text = "Ques" + quesNumber + "/" + quesList.size
        holder.showNumberOfQuestion.typeface = obj_UsefullData.get_Zamenhof_regular()


        val spanned = HtmlCompat.fromHtml(quesList[position].question!!, HtmlCompat.FROM_HTML_MODE_LEGACY)

        holder.showQuestion.text = spanned

        var incorrect = quesList[position].incorrectAnswers
        var corr = quesList[position].correctAnswer
        for (item: String in incorrect!!.listIterator()) {
            answerList.add(item)
        }

        answerList.add(corr!!)

        answerList.shuffle()

        for (i in 0 until answerList.size) {
            when {
                holder.option1.text == "" -> holder.option1.text = answerList[i]
                holder.option2.text == "" -> holder.option2.text = answerList[i]
                holder.option3.text == "" -> holder.option3.text = answerList[i]
                else -> holder.option4.text = answerList[i]
            }
        }



        holder.group.setOnCheckedChangeListener { group, checkedId ->
            val optionSelected :RadioButton = group!!.findViewById(checkedId)



            lastCheckedRB = optionSelected

//            lastCheckedRB!!.setOnClickListener {
//                if(mListener!= null){
//                    if(position != RecyclerView.NO_POSITION){
//                        mListener!!.onCustomItemClickListener(lastCheckedRB!!,position)
//                    }
//                }
//
//            }

//            if(mListener!= null){
//                mListener!!.onCustomItemClickListener(lastCheckedRB!!,position)
//            }

        }



//        holder.option2.setOnClickListener(this)


        holder.option1.setOnClickListener {
            recyclerView.scrollToPosition(position+1)
        }

    }


//    override fun onClick(v: View?) {
//        when(v!!.id){
//            R.id.radio1 ->
//        }
//    }

    class QuestionHolder(view: View) : RecyclerView.ViewHolder(view){

        // Holds the TextView that will add question

        val showNumberOfQuestion = view.numofQues!!
        val showQuestion = view.question!!

        val group =view.radioGroup!!

        val option1 = view.radio1!!


        val option2 = view.radio2!!
        val option3 = view.radio3!!
        val option4 = view.radio4!!





    }
}


