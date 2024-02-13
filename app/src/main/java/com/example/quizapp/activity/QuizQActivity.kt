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
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityQuizQactivityBinding
import com.example.quizapp.viewmodel.QuiqViewModel

class QuizQActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuizQactivityBinding
    private val totalTimeInMillis: Long = 10 * 60 * 1000
    lateinit var viewModel: QuiqViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(QuiqViewModel::class.java)

        viewModel.getJsonData()
        Log.d("hero2",viewModel.ListOf10Mcq.size.toString())
        updateQuestion()
        binding.NextBtn.setOnClickListener {

            radioBtnHandle()
            updateQuestion()

            binding.radioGroup.clearCheck()
        }
        timer()



    }
    override fun onBackPressed() {
        showExitConfirmationDialog(this)
    }
    private fun radioBtnHandle(){
        if (binding.option1.isChecked){
            viewModel.ListOfUserAns.add("a")
        }else if (binding.option2.isChecked){
            viewModel.ListOfUserAns.add("b")
        }else if(binding.option3.isChecked){
            viewModel.ListOfUserAns.add("c")
        }else{
            viewModel.ListOfUserAns.add("d")
        }
    }

     private fun passResult(){

         val intent=Intent(this,ResultActivity::class.java)
         Log.d("hero","checker ${viewModel.ListOfUserAns.toString()}")

         intent.putExtra("userans",viewModel.ListOfUserAns)


         intent.putExtra("mcq",viewModel.ListOf10Mcq)
         startActivity(intent)
         finish()
     }
    private fun updateQuestion() {



        if (viewModel.question_no<viewModel.ListOf10Mcq.size){
            val temp=viewModel.ListOf10Mcq[viewModel.question_no]
            binding.question.text=temp.question
            binding.QuestionNO.text="Question ${viewModel.question_no+1}"
            binding.option1.text=temp.a
            binding.option2.text=temp.b
            binding.option3.text=temp.c
            binding.option4.text=temp.d

        }else{
            Log.d("hero",viewModel.ListOfUserAns.toString())
            passResult()
        }
        viewModel.question_no+=1
    }



    private fun timer(){
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
        val secondRemaining=millisUntilFinished/1000
        val minutes = (millisUntilFinished / 1000) / 60
        val seconds = (millisUntilFinished / 1000) % 60
        val timeFormatted = String.format("%02d:%02d", minutes, seconds)
        binding.timer.text = " $timeFormatted"
        binding.progressBar.progress=(5*60 - secondRemaining).toInt()
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
}