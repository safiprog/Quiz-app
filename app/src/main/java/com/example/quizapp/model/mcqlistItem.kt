package com.example.quizapp.model

import java.io.Serializable


data class mcqlistItem(
    val a: String,
    val b: String,
    val c: String,
    val d: String,
    val id: Int,
    val key: String,
    val question: String
): Serializable