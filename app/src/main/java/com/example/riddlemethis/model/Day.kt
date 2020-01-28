package com.example.riddlemethis.model

import com.google.gson.annotations.SerializedName

// only get the dayOfTheWeek value out of the API call.
data class Day(
    @SerializedName("dayOfTheWeek") var dayOfTheWeak: String
)