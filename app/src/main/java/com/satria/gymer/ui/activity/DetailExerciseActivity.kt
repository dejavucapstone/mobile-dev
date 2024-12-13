package com.satria.gymer.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.satria.gymer.R
import com.satria.gymer.network.ApiClient
import com.satria.gymer.network.ApiService
import com.satria.gymer.network.response.DataItems
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_exercise)

        // Inisialisasi views
        val backButton: ImageView = findViewById(R.id.backButton)
        val exerciseTitle: TextView = findViewById(R.id.exerciseTitle)
        val descriptionText: TextView = findViewById(R.id.descriptionText)
        val benefitsText: TextView = findViewById(R.id.benefitsText)
        val howToPerformText: TextView = findViewById(R.id.howToPerformText)
        val tipsText: TextView = findViewById(R.id.tipsText)

        // Mendapatkan ID latihan dari intent
        val exerciseId = intent.getIntExtra("EXERCISE_ID", -1)

        // Memanggil API untuk mendapatkan detail latihan berdasarkan ID
        if (exerciseId != -1) {
            fetchExerciseDetails(exerciseId, exerciseTitle, descriptionText, benefitsText, howToPerformText, tipsText)
        }

        // Fungsi untuk menutup activity saat back button ditekan
        backButton.setOnClickListener {
            finish() // Menutup activity
        }
    }

    // Fungsi untuk mengambil detail latihan dari API
    private fun fetchExerciseDetails(
        exerciseId: Int,
        exerciseTitle: TextView,
        descriptionText: TextView,
        benefitsText: TextView,
        howToPerformText: TextView,
        tipsText: TextView
    ) {
        // Menghubungkan ke API menggunakan Retrofit
        val apiService = ApiClient.getClient().create(ApiService::class.java)
        apiService.getExerciseById(exerciseId).enqueue(object : Callback<DataItems> {
            override fun onResponse(call: Call<DataItems>, response: Response<DataItems>) {
                if (response.isSuccessful) {
                    val exercise = response.body()
                    exercise?.let {
                        // Set data dari API ke TextView
                        exerciseTitle.text = it.namaExercise
                        descriptionText.text = it.description
                        benefitsText.text = it.howToDo // Atau bisa diubah jika data berbeda
                        howToPerformText.text = it.howToDo // Sesuaikan data yang diinginkan
                        tipsText.text = it.trainingTips
                    }
                } else {
                    Toast.makeText(this@DetailExerciseActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataItems>, t: Throwable) {
                Toast.makeText(this@DetailExerciseActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
