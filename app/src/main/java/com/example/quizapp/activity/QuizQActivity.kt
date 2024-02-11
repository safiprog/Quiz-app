package com.example.quizapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityQuizQactivityBinding

class QuizQActivity : AppCompatActivity() {
    lateinit var binding:ActivityQuizQactivityBinding
    private val totalTimeInMillis: Long = 1 * 60 * 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityQuizQactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCountDownTimer()
    }

    private fun startCountDownTimer() {
        object : CountDownTimer(totalTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateProgressBar(millisUntilFinished)
            }

            override fun onFinish() {
                // Handle completion, if needed
                binding.progressBar.progress = binding.progressBar.max
                Toast.makeText(this@QuizQActivity, "hello brotehr i", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun updateProgressBar(millisUntilFinished: Long) {
        val progress = ((totalTimeInMillis - millisUntilFinished) / 1000).toInt()
        binding.progressBar.progress = progress
    }
}