package com.example.quizapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityResultBinding
import com.example.quizapp.model.mcqlistItem
import com.example.quizapp.viewmodel.QuiqViewModel
import java.io.Serializable

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        val userAnswers = intent.getSerializableExtra("userans") as ArrayList<*>
        val questionList = intent.getSerializableExtra("mcq") as ArrayList<mcqlistItem>
        val editor = getSharedPreferences("My_Satting", MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
        updateUi(userAnswers,questionList)
        binding.restartQ.setOnClickListener {
            val intent=Intent(this,QuizQActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateUi(userAnswers: java.util.ArrayList<*>, questionList: java.util.ArrayList<mcqlistItem>) {
        val data=calAns(userAnswers,questionList)
        data?.let {

            binding.rightQuestion.text=it[0].toString()
            binding.wrongQuestion.text=it[1].toString()
            binding.textSper.text="${it[0]*10}%"
        }

    }


    private fun calAns(
        userAns: ArrayList<*>,
        questionList: ArrayList<mcqlistItem>
    ): ArrayList<Int> {
        var data = ArrayList<Int>()
        var right = 0
        var wrong = 0
        for (i in 0..questionList.size - 1) {
            when (userAns[i]) {
                "a" -> {
                    if (questionList[i].a == questionList[i].key) {
                        right++;
                    } else wrong++;
                }

                "b" -> {
                    if (questionList[i].b == questionList[i].key) {
                        right++;
                    } else wrong++;
                }

                "c" -> {
                    if (questionList[i].c == questionList[i].key) {
                        right++;
                    } else wrong++;
                }

                "d" -> {
                    if (questionList[i].d == questionList[i].key) {
                        right++;
                    } else wrong++;
                }
            }
        }
        data.add(right)
        data.add(wrong)
        return data
    }
    override fun onBackPressed() {
        showExitConfirmationDialog(this)
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