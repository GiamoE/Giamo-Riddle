package com.example.riddlemethis.api

import com.example.riddlemethis.model.Day
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DayApiService {
    // The GET method needed to retrieve a random number trivia.
    @GET("/api/json/cet/now?")
    fun getCurrentDay(): Call<Day>
}