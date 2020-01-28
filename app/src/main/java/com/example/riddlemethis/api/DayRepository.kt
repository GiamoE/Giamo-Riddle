package com.example.riddlemethis.api

class DayRepository {

    /*
    In the repository class a variable is created for the api service.
    The method getCurrentDay returns a call from the repository
     */
    private val dayApi: DayApiService = DayApi.createApi()

    fun getCurrentDay() = dayApi.getCurrentDay()
}