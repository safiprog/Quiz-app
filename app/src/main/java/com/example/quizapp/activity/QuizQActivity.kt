package com.example.quizapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityQuizQactivityBinding

class QuizQActivity : AppCompatActivity() {
    lateinit var binding:ActivityQuizQactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityQuizQactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startProgressBar()
    }

    private fun startProgressBar() {
        val totalDuration = 1 * 60 * 1000L // 10 minutes in milliseconds

        val updateInterval = 100L // Update every 100 milliseconds

        object : CountDownTimer(totalDuration, updateInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = 1000 - (millisUntilFinished / updateInterval) // Calculate progress
                binding.progressBar.progress = progress.toInt()
            }

            override fun onFinish() {
                binding.progressBar.progress = 1000 // Set progress to maximum at the end
                // Handle end of progress bar here (e.g., show a message)
                Toast.makeText(this@QuizQActivity, "it done", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}