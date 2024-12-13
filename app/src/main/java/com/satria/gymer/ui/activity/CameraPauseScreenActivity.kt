package com.satria.gymer.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.satria.gymer.R
import com.satria.gymer.ml.GymEquipClassifierMetadata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.label.Category


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
            CoroutineScope(Dispatchers.Main).launch {
                val model = GymEquipClassifierMetadata.newInstance(this@CameraPauseScreenActivity)
                val image = TensorImage.fromBitmap(uriToBitmap(Uri.parse(imageUri)))
                val outputs = model.process(image)
                val probability = outputs.probabilityAsCategoryList

                var max:Category = Category("",0.0F)
                for (p in probability) {
                    Log.d("TAG", "${p.index} - ${p.displayName} - ${p.label} - ${p.score}")
                    if(max.score<p.score){
                        max=p
                    }
                }


                model.close()

                // Show confirmation message
                Toast.makeText(this@CameraPauseScreenActivity, "Photo confirmed!", Toast.LENGTH_SHORT).show()

                // Navigate to AnalysisListActivity
                val intent = Intent(this@CameraPauseScreenActivity, AnalysisListActivity::class.java)
                    .putExtra("imageUri", imageUri)
                    .putExtra("name", max.label)
                startActivity(intent)

                // Optionally, finish this activity if you don't want the user to return here
                finish()
            }
        }
    }

    fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
