package com.example.timetable.data

import com.example.timetable.R

val logos = mapOf(
    2 to R.drawable.accountant,
    9 to R.drawable.university,
    10 to R.drawable.welding,
    11 to R.drawable.electric,
    19 to R.drawable.network,
    23 to R.drawable.sos,
    24 to R.drawable.admin,
    25 to R.drawable.computer,
    26 to R.drawable.transport,
    27 to R.drawable.cook
)

data class Group(
    val Key: Int,
    val Name: String,
    val Year: Int,
    val Spec: Int
)

data class Teacher(
    val Key: Int,
    val Teacher: String
)

data class Room(
    val Key: Int,
    val UTF: String,
)

data class Period(
    val Key: Int,
    val Position: Int,
    val Begin: String,
    val End: String
)

data class Subject(
    val Key: Int,
    val Subject: String,
    val Hours: Int,
    val First: Int
)

data class Lesson(
    val Day: Int,
    val Pair: Int,
    val Subj: Int,
    val Teacher: Int?,
    val Room: Int?,
)