package com.example.signingoogle.API

import com.example.signingoogle.pojo.CategoryResponse
import com.example.signingoogle.pojo.QuestionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {


    @GET("api_category.php")
    fun getCategoryList(): Call<CategoryResponse>


    @GET("api.php")
    fun getQuestionList(
        @Query("amount") amt_of_ques: String?,
        @Query("category") category: Int?,
        @Query("difficulty") diffcultParam: String?,
        @Query("type") type: String) : Call<QuestionResponse>

    @GET("api.php")
    fun getQuestionList(@Query("amount") amt_of_ques: String?) : Call<QuestionResponse>

}