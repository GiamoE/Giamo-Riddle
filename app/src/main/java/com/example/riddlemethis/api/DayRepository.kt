package com.example.riddlemethis.api

class DayRepository {

    /*
    In the repository class a variable is created for the api service.
    The method getRandomNumberTrivia returns the Call<Trivia> from the api service.
     */
    private val dayApi: DayApiService = DayApi.createApi()

    fun getCurrentDay() = dayApi.getCurrentDay()
}