package com.example.headsupgame

import android.text.Editable
import retrofit2.Call
import retrofit2.http.*

interface APIInterface{

    @GET("celebrities/")
    fun getTest(): Call<ArrayList<CelebrityItem>>


    @POST("celebrities/")
    fun postTest(@Body testData: CelebrityItem): Call<CelebrityItem>


    //updating
    @PUT("/celebrities/{id}") //pass id to modify
    fun updateTest(@Path("id")id: Int, @Body testData: CelebrityItem): Call<CelebrityItem>

    @DELETE("/celebrities/{id}")
    fun deleteTest(@Path("id") id: Int): Call<Void>//void to override an existing post
}


















