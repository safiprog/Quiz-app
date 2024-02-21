package com.example.quizapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityQuizQactivityBinding
import com.example.quizapp.viewmodel.QuiqViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class QuizQActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuizQactivityBinding
    private val totalTimeInMillis: Long = 10 * 60 * 1000
    lateinit var viewModel: QuiqViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(QuiqViewModel::class.java)



        val data=getSharedPreferences("My_Satting", MODE_PRIVATE)
        val qNo=data.getInt("currentNo",0)
        Log.d("hello","question number value $qNo")
        if(qNo>0 && qNo!=11){
            askResume(this)
        }else{
            viewModel.generateUniqueRandomNumbers()
            viewModel.getJsonData(viewModel.generateUniqueRandomNumbers())
            updateQuestion()
        }
//        if (qNo>0){
//            userOldQuiz()
//
//            Log.e("hello",viewModel.range.toString())
//            Log.e("hello",viewModel.question_no.toString())
//            Log.e("hello",viewModel.ListOfUserAns.toString())
//
//        }else{
//            viewModel.generateUniqueRandomNumbers()
//            viewModel.getJsonData(viewModel.generateUniqueRandomNumbers())
//            userOldQuiz()
//            updateQuestion()
//        }


//

        binding.NextBtn.setOnClickListener {

            val temp = radioBtnHandle()
            if (temp)
                updateQuestion()

                binding.radioGroup.clearCheck()
//            } else {
//                Toast.makeText(this, "Please Select Answer ", Toast.LENGTH_SHORT).show()
//            }

        }
        timer()


    }



    override fun onStop() {
        super.onStop()
        if (viewModel.question_no>1) {
            Log.e("hello","save data call success fully")
            saveData()
        }
    }

    override fun onBackPressed() {
        showExitConfirmationDialog(this)
    }

    private fun radioBtnHandle(): Boolean {
        var temp = false
        if (binding.option1.isChecked) {
            viewModel.ListOfUserAns.add("a")
            temp = true
        } else if (binding.option2.isChecked) {
            viewModel.ListOfUserAns.add("b")
            temp = true
        } else if (binding.option3.isChecked) {
            viewModel.ListOfUserAns.add("c")
            temp = true
        } else if (binding.option4.isChecked) {
            viewModel.ListOfUserAns.add("d")
            temp = true
        }
        return temp
    }

    private fun passResult() {

        val intent = Intent(this, ResultActivity::class.java)
        Log.d("hero", "checker ${viewModel.ListOfUserAns.toString()}")

        intent.putExtra("userans", viewModel.ListOfUserAns)


        intent.putExtra("mcq", viewModel.ListOf10Mcq)
        startActivity(intent)
        finish()
    }

    private fun updateQuestion() {


        if (viewModel.question_no < viewModel.ListOf10Mcq.size) {
            val temp = viewModel.ListOf10Mcq[viewModel.question_no]
            binding.question.text = temp.question
            binding.QuestionNO.text = "Question ${viewModel.question_no + 1}"
            binding.option1.text = temp.a
            binding.option2.text = temp.b
            binding.option3.text = temp.c
            binding.option4.text = temp.d

        } else {
            Log.d("hero", viewModel.ListOfUserAns.toString())
            passResult()
        }
        viewModel.question_no += 1
    }


    private fun timer() {
        val countDownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                updateTimer(millisUntilFinished)
            }

            override fun onFinish() {

                // Perform any action you want when the timer finishes
            }
        }

        // Start the timer
        countDownTimer.start()
    }


    private fun updateTimer(millisUntilFinished: Long) {
        val secondRemaining = millisUntilFinished / 1000
        val minutes = (millisUntilFinished / 1000) / 60
        val seconds = (millisUntilFinished / 1000) % 60
        val timeFormatted = String.format("%02d:%02d", minutes, seconds)
        binding.timer.text = " $timeFormatted"
        binding.progressBar.progress = (10 * 50 - secondRemaining).toInt()
    }

    fun showExitConfirmationDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Exit Confirmation")
        builder.setMessage("Are you sure you want to exit the app?")
        // Set affirmative and negative buttons
        builder.setPositiveButton("Exit") { dialog, _ ->
            dialog.dismiss()
            finishAffinity() // Properly exit the app
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
    fun askResume(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Restart Old Quiz")
        builder.setMessage("are you want to resume the previous quiz?")
        // Set affirmative and negative buttons
        builder.setPositiveButton("No") { dialog, _ ->
            dialog.dismiss()
            viewModel.generateUniqueRandomNumbers()
            viewModel.getJsonData(viewModel.generateUniqueRandomNumbers())
            updateQuestion()
             // Properly exit the app
        }
        builder.setNegativeButton("yes") { dialog, _ ->
            dialog.dismiss()


            userOldQuiz()
            updateQuestion()
        }
        builder.create().show()
    }


    fun saveData() {
        val gson=Gson()
        val jsonString = gson.toJson(viewModel.range)
        val jsonUserAns=gson.toJson(viewModel.ListOfUserAns)
        val editor = getSharedPreferences("My_Satting", MODE_PRIVATE).edit()
        editor.putInt("currentNo",viewModel.question_no)
        editor.putString("userAns",jsonUserAns)
        editor.putString("range",jsonString)

        editor.apply()
    }

    fun userOldQuiz() {
        val data = getSharedPreferences("My_Satting", MODE_PRIVATE)
        val qNo = data.getInt("currentNo", 0)
        val userAns = data.getString("userAns", null)
        val range = data.getString("range", null)
        val gson = Gson()
        val rangeArray =
            gson.fromJson<ArrayList<Int>>(range, object : TypeToken<ArrayList<Int>>() {}.type)
        val userAnsArray = gson.fromJson<ArrayList<String>>(userAns,
            object : TypeToken<ArrayList<String>>() {}.type)

        try {
            viewModel.question_no = qNo - 1
            viewModel.ListOfUserAns = userAnsArray
            viewModel.range = rangeArray
            viewModel.getJsonData(viewModel.range)
            Log.d("hello",userAnsArray.toString())
            Log.d("hello",rangeArray.toString())
            Log.d("hello",qNo.toString())

        } catch (e: Exception) {

        Log.d("hello", e.message.toString())
    }


    }



}