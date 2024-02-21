package com.example.quizapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.quizapp.model.mcqlistItem
import com.example.quizapp.util.JsonUtils
import kotlin.random.Random

class QuiqViewModel(application: Application) :AndroidViewModel(application) {
    val appContext = getApplication<Application>().applicationContext

    var question_no=0;
    var ListOf10Mcq=ArrayList<mcqlistItem>()
    var ListOfUserAns=ArrayList<String>()
    var range=ArrayList<Int>()

    fun generateUniqueRandomNumbers(): ArrayList<Int> {
        val uniqueNumbersSet = mutableSetOf<Int>()
        val random = Random

        // Keep generating random numbers until 10 unique ones are found
        while (uniqueNumbersSet.size < 10) {
            val randomNumber = random.nextInt(100)
            uniqueNumbersSet.add(randomNumber)
        }
        range=ArrayList(uniqueNumbersSet)
        return ArrayList(uniqueNumbersSet)
    }

     fun getJsonData(range:ArrayList<Int>) {

        val items = JsonUtils.getItemsFromJson(appContext, "output.json")
         for (index in range){
             ListOf10Mcq.add(items[index])
         }
    }


}