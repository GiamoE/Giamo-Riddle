package com.example.riddlemethis.model

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("dayOfTheWeek") var dayOfTheWeak: String
)