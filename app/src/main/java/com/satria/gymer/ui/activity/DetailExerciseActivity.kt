package com.satria.gymer.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.satria.gymer.R
<<<<<<< HEAD
import com.satria.gymer.data.model.ErrorResponse
import com.satria.gymer.data.model.exercise.DetailExerciseResponse
import com.satria.gymer.data.model.history.DetailHistoryResponse
import com.satria.gymer.data.network.ApiConfig
import com.satria.gymer.databinding.ActivityDetailExerciseBinding
import com.satria.gymer.databinding.ActivityDetailHistoryBinding
import com.satria.gymer.utils.LoadingDialogUtils
import com.satria.gymer.utils.SharedPrefUtils
=======
import com.satria.gymer.network.ApiClient
import com.satria.gymer.network.ApiService
import com.satria.gymer.network.response.DataItems
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD
        binding = ActivityDetailExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra("id")!!
        with(binding){
            backButton.setOnClickListener {
                finish()
            }
=======
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
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
        }
        val loadingDialog = LoadingDialogUtils(this)

        loadingDialog.show()
        val client = ApiConfig.getApiService(SharedPrefUtils.getAuthToken(this))
            .getDetailExercises(id)
        client.enqueue(object : Callback<DetailExerciseResponse> {
            override fun onResponse(
                call: Call<DetailExerciseResponse>,
                response: Response<DetailExerciseResponse>
            ) {
                loadingDialog.dismiss()
                if (response.isSuccessful) {
                    response.body()?.let { exerciseResponse ->
                        with(binding){
                            exerciseTitle.text = exerciseResponse.data.exerciseName
                            Glide.with(this@DetailExerciseActivity).load(exerciseResponse.data.imageUrl).into(imagePlaceholder)
                            descriptionText.text = exerciseResponse.data.description
                            benefitsText.text = exerciseResponse.data.bodyPart
                            howToPerformText.text = exerciseResponse.data.howToDo
                            tipsText.text = exerciseResponse.data.trainingTips
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                            Toast.makeText(this@DetailExerciseActivity,errorResponse.message, Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<DetailExerciseResponse>, t: Throwable) {
                loadingDialog.dismiss()
            }
        })
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
