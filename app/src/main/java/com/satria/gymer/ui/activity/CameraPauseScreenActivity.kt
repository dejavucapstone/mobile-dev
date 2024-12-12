package com.satria.gymer.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.satria.gymer.R

class CameraPauseScreenActivity : AppCompatActivity() {

    private lateinit var closeButton: ImageButton
    private lateinit var confirmButton: ImageButton
    private lateinit var imageView: ImageView
    private var imageUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_pause_screen)

        closeButton = findViewById(R.id.closeButton)
        confirmButton = findViewById(R.id.confirmButton)
        imageView = findViewById(R.id.capturedImageView)

        // Get the image URI from intent
        imageUri = intent.getStringExtra("imageUri")

        // Display the captured image
        if (imageUri != null) {
            Glide.with(this)
                .load(Uri.parse(imageUri))
                .into(imageView)
        } else {
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
        }

        // Set up button listeners
        closeButton.setOnClickListener {
            // Go back to CameraXActivity
            finish()
        }

        confirmButton.setOnClickListener {
            // Show confirmation message
            Toast.makeText(this, "Photo confirmed!", Toast.LENGTH_SHORT).show()

            // Navigate to AnalysisListActivity
            val intent = Intent(this, AnalysisListActivity::class.java)
            startActivity(intent)

            // Optionally, finish this activity if you don't want the user to return here
            finish()
        }
    }
}
