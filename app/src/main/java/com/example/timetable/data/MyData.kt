package com.example.timetable.data

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