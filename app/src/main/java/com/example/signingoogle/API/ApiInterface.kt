package com.example.signingoogle.API

import com.example.signingoogle.pojo.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {


    @GET("api_category.php")
    fun getCategoryList(): Call<CategoryResponse>



}