package com.example.ambrosia

import android.util.Log
import com.example.ambrosia.Models.GeminiRequest

class OpenAIRepository {
    private val apiService = RetroInstance.geminiInstance

    suspend fun getDishSuggestions(ingredients: String): String {
        val prompt = "Act as a professional chef who can cook all the dishes from the world, give response according to the query ${ingredients}"
//        val apiKey = BuildConfig.GEMINI_API_KEY
        val apiKey = "AIzaSyA4YiCvLKgt2984Md1rPxcviJ9rZyraWOc"

        return try {
            val request = GeminiRequest(
                contents = listOf(
                    GeminiRequest.Content(
                        parts = listOf(
                            GeminiRequest.Part(text = prompt)
                        )
                    )
                )
            )

            val response = apiService.generateContent(apiKey, request)

            if (response.isSuccessful) {
                response.body()?.candidates?.firstOrNull()
                    ?.content?.parts?.firstOrNull()?.text
                    ?.replace("*", "•")  // Format bullets
                    ?: "No suggestions found"
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("API_ERROR", "Error response: $errorBody")
                "API Error: ${response.code()} - ${response.message()}"
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception: ${e.stackTraceToString()}")
            "Network Error: ${e.localizedMessage}"

        }
    }
}