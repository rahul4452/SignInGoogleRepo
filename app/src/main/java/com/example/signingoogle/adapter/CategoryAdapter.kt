package com.example.signingoogle.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.signingoogle.R
import com.example.signingoogle.roomDatabase.Category
import com.example.signingoogle.taketest.StartQuiz
import com.example.signingoogle.utilities.UseFull



class CategoryAdapter(items: ArrayList<Category>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    var categoryList: ArrayList<Category> = items
    var ctx: Context = context
    private lateinit var obj_UsefullData: UseFull

    // Gets the size in the list
    override fun getItemCount(): Int {
        return categoryList.size
    }


    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.category_list_recycle, parent, false))

    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        obj_UsefullData = object : UseFull(ctx) {}

        holder.categoryName.text = categoryList[position].category_name
        holder.categoryName.typeface = obj_UsefullData.Aller_bd()

        holder.setOnCustomItemClickListener(object :CustomItemClickListner{

            override fun onCustomItemClickListener(view: View, pos: Int) {


                var cid = categoryList[pos].category_id

                if(cid==null){

                    var categoryName = categoryList[pos].category_name
                    val intent2 = Intent(ctx, StartQuiz::class.java)
                    intent2.putExtra("categoryName", categoryName)
                    ctx.startActivity(intent2)

                }else {
                    val intent1 = Intent(ctx, StartQuiz::class.java)
                    intent1.putExtra("category_id", cid)
                    ctx.startActivity(intent1)
                }
            }
        })


    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {


    // Holds the TextView that will add each animal to

     var categoryName: TextView
    var customItemclick : CustomItemClickListner ?= null

    init {
        categoryName =  view.findViewById(R.id.categoryName)
        view.setOnClickListener(this)
    }

    fun setOnCustomItemClickListener(itemClickListner: CustomItemClickListner) {

        this.customItemclick = itemClickListner
    }


    override fun onClick(v: View?) {
        this.customItemclick!!.onCustomItemClickListener(v!!,adapterPosition)
    }



}



