package com.example.riddlemethis.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


// we use parcelable to serialiaze a class so the properties can be transferred from one
// activity to another. And it's faster than serialization
@Parcelize
@Entity(tableName = "riddleTable")
data class Riddle(
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "answer")
    var answer: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
) : Parcelable