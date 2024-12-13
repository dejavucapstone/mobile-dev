package com.satria.gymer.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.satria.gymer.R
import com.satria.gymer.data.model.ErrorResponse
import com.satria.gymer.data.model.exercise.DetailExerciseResponse
import com.satria.gymer.data.model.history.DetailHistoryResponse
import com.satria.gymer.data.network.ApiConfig
import com.satria.gymer.databinding.ActivityDetailExerciseBinding
import com.satria.gymer.databinding.ActivityDetailHistoryBinding
import com.satria.gymer.utils.LoadingDialogUtils
import com.satria.gymer.utils.SharedPrefUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra("id")!!
        with(binding){
            backButton.setOnClickListener {
                finish()
            }
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
}
