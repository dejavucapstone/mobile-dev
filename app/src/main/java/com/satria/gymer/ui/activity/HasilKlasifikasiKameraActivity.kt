package com.satria.gymer.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.satria.gymer.R
import org.tensorflow.lite.Interpreter
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*

class HasilKlasifikasiKameraActivity : AppCompatActivity() {

    private lateinit var interpreter: Interpreter
    private val classNames = listOf(
        "Chest Fly Machine",
        "Dumbbell",
        "Lat Pull Down Machine",
        "Leg Extension Machine",
        "Leg Press Machine",
        "Seated Row Machine",
        "Smith Machine",
        "Treadmill"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_klasifikasi_kamera)

        val imageUri = intent.getStringExtra("imageUri")
        if (imageUri != null) {
            classifyImage(Uri.parse(imageUri))
        } else {
            Toast.makeText(this, "No image found", Toast.LENGTH_SHORT).show()
        }

        // Tombol untuk memilih gambar dari galeri
        findViewById<Button>(R.id.btn_select_image).setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                classifyImage(uri)
            }
        }
    }

    private fun classifyImage(imageUri: Uri) {
        try {
            // Load model
            val model = loadModelFile("gym_equip_classifier_metadata.tflite")
            interpreter = Interpreter(model)

            // Load and preprocess image
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
            val processedImage = preprocessImage(bitmap)

            // Run inference
            val output = Array(1) { FloatArray(classNames.size) }
            interpreter.run(processedImage, output)

            // Get classification result
            val probabilities = output[0]
            val maxIndex = probabilities.indices.maxByOrNull { probabilities[it] } ?: -1
            val label = classNames[maxIndex]
            val confidence = probabilities[maxIndex] * 100

            // Display result
            findViewById<ImageView>(R.id.resultImage).setImageBitmap(bitmap)
            findViewById<TextView>(R.id.resultText).text = "$label: ${"%.2f".format(confidence)}%"

        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun preprocessImage(bitmap: Bitmap): ByteBuffer {
        val inputSize = 224 // Expected input size for the model
        val pixelSize = 3 // RGB channels

        // Resize the bitmap to the required input size
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)

        // Prepare a ByteBuffer for the model input
        val byteBuffer = ByteBuffer.allocateDirect(inputSize * inputSize * pixelSize * 4)
        byteBuffer.order(ByteOrder.nativeOrder())

        // Normalize pixel values and add them to the buffer
        val intValues = IntArray(inputSize * inputSize)
        resizedBitmap.getPixels(intValues, 0, inputSize, 0, 0, inputSize, inputSize)
        for (pixel in intValues) {
            val r = (pixel shr 16 and 0xFF) / 255.0f
            val g = (pixel shr 8 and 0xFF) / 255.0f
            val b = (pixel and 0xFF) / 255.0f
            byteBuffer.putFloat(r)
            byteBuffer.putFloat(g)
            byteBuffer.putFloat(b)
        }

        return byteBuffer
    }

    private fun loadModelFile(modelPath: String): MappedByteBuffer {
        val fileDescriptor = assets.openFd(modelPath)
        val inputStream = fileDescriptor.createInputStream()
        val fileChannel = inputStream.channel
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.startOffset, fileDescriptor.declaredLength)
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 100
    }
}
