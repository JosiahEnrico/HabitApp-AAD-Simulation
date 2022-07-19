package com.aadexercise.habitapp.data

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//TODO 1 : Define a local database table using the schema in app/schema/habits.json
@Entity(tableName = "habits")
@Parcelize
data class Habit(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    @NonNull
    val title: String,

    @ColumnInfo(name = "minutesFocus")
    @NonNull
    val minutesFocus: Long,

    @ColumnInfo(name = "startTime")
    @NonNull
    val startTime: String,

    @ColumnInfo(name = "priorityLevel")
    @NonNull
    val priorityLevel: String

): Parcelable
