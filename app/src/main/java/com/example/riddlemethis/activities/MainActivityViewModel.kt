package com.example.riddlemethis.activities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.riddlemethis.api.DayRepository
import com.example.riddlemethis.model.Day
import com.example.riddlemethis.model.Riddle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    // the day value is saved even if there are configuration changes due to the vm
    // LiveDate is used as an observable that is aware of the lifecycle.
    private val dayRepository = DayRepository()
    val day = MutableLiveData<Day>()
    val error = MutableLiveData<String>()


    // if the response is succesful fill the day object with the response.
    // else set an error message
    fun getCurrentDay() {
        dayRepository.getCurrentDay().enqueue(object : Callback<Day> {
            override fun onResponse(call: Call<Day>, response: Response<Day>) {
                if (response.isSuccessful) day.value = response.body()
                else error.value = "An error occurred: ${response.errorBody().toString()}"
            }
            override fun onFailure(call: Call<Day>, t: Throwable) {
                error.value = t.message
            }
        })
    }

}
