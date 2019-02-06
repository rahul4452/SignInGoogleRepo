package com.example.signingoogle.taketest

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.signingoogle.API.ApiClient
import com.example.signingoogle.API.ApiInterface

import com.example.signingoogle.adapter.CategoryAdapter
import com.example.signingoogle.pojo.CategoryResponse
import com.example.signingoogle.roomDatabase.Category
import com.example.signingoogle.roomDatabase.QuizDatabase
import kotlinx.android.synthetic.main.activity_categories.*

import kotlinx.android.synthetic.main.content_categories.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.graphics.Color
import android.widget.TextView

import android.view.View
import com.androidadvance.topsnackbar.TSnackbar
import com.example.signingoogle.R

import com.example.signingoogle.utilities.UseFull
import kotlinx.android.synthetic.main.activity_start_quiz.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class Categories : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    SearchView.OnQueryTextListener {

    private lateinit var quizDb: QuizDatabase
    private lateinit var category_adapter: CategoryAdapter
    private lateinit var cate : Category
    private var service: ApiInterface? = null
    val temp = ArrayList<Category>()
    lateinit var obj_UsefullData: UseFull

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        setSupportActionBar(toolbar)

        initUI()
        // ============= Intialize database ============== //



        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)



        doAsync {
            val count = quizDb.quizCategoryInterface().usersCount()


            // getting Category List From DB

            var categoryList : List<Category> ?= quizDb.quizCategoryInterface().getCategoryData()


            uiThread {

                if(count<1){

                    callToServer()

                }
                else{

//                    temp.clear()
//
//                    if (categoryList != null) {
//
//                        for(item in categoryList) {
//
////                            temp.add(item.category_name!!)
//
//                        }
//                    }

                    category_adapter = CategoryAdapter((categoryList as ArrayList<Category>?)!!,this@Categories)

                    my_recycler_view.adapter = category_adapter




                    //Add a LayoutManager
                    my_recycler_view.layoutManager = LinearLayoutManager(this@Categories, RecyclerView.VERTICAL, false)

                }
            }
        }
    }


    private fun initUI() {

        obj_UsefullData = object : UseFull(applicationContext) {}
        quizDb = QuizDatabase.getInstance(applicationContext)!!

        service = ApiClient().getClient()?.create(ApiInterface::class.java)

        toolbar_title.typeface = obj_UsefullData.Aller_Rg()
        toolbar_title.text = "Category"



    }


    private fun callToServer() {
        val responscall : Call<CategoryResponse> = service!!.getCategoryList()

        responscall.enqueue(object : Callback<CategoryResponse>{

            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {

                try {

                    if(response.isSuccessful){

                        val categoryList: List<CategoryResponse.TriviaCategory> = response.body()?.triviaCategories!!



                        doAsync {

                            for(item:CategoryResponse.TriviaCategory in categoryList.listIterator()){

                                cate = Category(null,item.name,item.id)

                                temp.add(cate)

                                quizDb.quizCategoryInterface().insert(cate)
                            }

                            uiThread {
                                category_adapter = CategoryAdapter(temp,this@Categories)

                                my_recycler_view.adapter = category_adapter

                                //Add a LayoutManager
                                my_recycler_view.layoutManager = LinearLayoutManager(this@Categories, RecyclerView.VERTICAL, false)
                            }

                        }

                    }

                }catch (e : Exception) {

                    e.printStackTrace()

                }

            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
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


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.categories, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchMenuItem = menu.findItem(R.id.action_search)
        var searchView = searchMenuItem.getActionView() as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)

        return true
    }



    override fun onQueryTextSubmit(query: String?): Boolean {
        return true

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_search -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_profile -> {
                // Handle the camera action
            }
            R.id.nav_favourite -> {

            }
            R.id.nav_leaderboard -> {

            }
            R.id.nav_faq -> {

            }
            R.id.nav_settings -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
