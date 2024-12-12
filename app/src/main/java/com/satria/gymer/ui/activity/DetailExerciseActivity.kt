package com.satria.gymer.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.satria.gymer.R

class DetailExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_exercise)

        val backButton: ImageView = findViewById(R.id.backButton)
        val screenTitle: TextView = findViewById(R.id.screenTitle)
        val exerciseTitle: TextView = findViewById(R.id.exerciseTitle)
        val descriptionText: TextView = findViewById(R.id.descriptionText)
        val benefitsText: TextView = findViewById(R.id.benefitsText)
        val howToPerformText: TextView = findViewById(R.id.howToPerformText)
        val tipsText: TextView = findViewById(R.id.tipsText)

        // Retrieve the data from the intent
        val title = intent.getStringExtra("EXERCISE_TITLE") ?: "No Title"
        val description = intent.getStringExtra("EXERCISE_DESCRIPTION") ?: "No Description"

        // Set the values into the views
        exerciseTitle.text = title
        descriptionText.text = description
        benefitsText.text = "1. Benefit 1\n2. Benefit 2"
        howToPerformText.text = "Steps to perform the exercise"
        tipsText.text = "Tips for better performance"

        // Set the back button functionality
        backButton.setOnClickListener {
            finish() // Close the activity and return to the previous screen
        }
    }
}
