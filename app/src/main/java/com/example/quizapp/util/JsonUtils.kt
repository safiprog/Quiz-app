package com.example.quizapp.util

import android.content.Context
import com.example.quizapp.model.mcqlistItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object JsonUtils {

    fun getItemsFromJson(context: Context, fileName: String): List<mcqlistItem> {
        val jsonString = loadJsonFromAsset(context, fileName)
        return Gson().fromJson(jsonString, object : TypeToken<List<mcqlistItem>>() {}.type)
            ?: emptyList()
    }

    private fun loadJsonFromAsset(context: Context, fileName: String): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }
}
