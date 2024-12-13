package com.satria.gymer.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.satria.gymer.R
import com.satria.gymer.data.model.ErrorResponse
import com.satria.gymer.data.model.history.DetailHistoryResponse
import com.satria.gymer.data.network.ApiConfig
import com.satria.gymer.databinding.ActivityDetailHistoryBinding
import com.satria.gymer.ui.adapter.ExerciseAdapter
import com.satria.gymer.utils.LoadingDialogUtils
import com.satria.gymer.utils.SharedPrefUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class DetailHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoryBinding
    private lateinit var adapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data HistoryItem dari Intent
        val id = intent.getStringExtra("id")!!

        // Periksa apakah data ada dan setel data ke UI

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnShare.setOnClickListener {
            Glide.with(this)
                .asBitmap()
                .load("https://rukminim2.flixcart.com/image/850/1000/xif0q/dumbbell/j/u/e/gym-dumbbell-set-2kg-x-2pcs-pvc-dumbell-pair-for-home-gym-original-imagtp9nnkw37wmq.jpeg?q=90&crop=false")
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        shareImage(resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
        val loadingDialog = LoadingDialogUtils(this)
        binding.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this, R.style.CustomDialogStyle)
            builder.setTitle("Confirm Action")
                .setMessage("Are you sure you want to proceed?")
                .setPositiveButton("Yes") { dialog, _ ->
                    loadingDialog.show()
                    // Call the confirm action when "Yes" is clicked
                    val client = ApiConfig.getApiService(SharedPrefUtils.getAuthToken(this))
                        .deleteHistory(id)
                    client.enqueue(object : Callback<DetailHistoryResponse> {
                        override fun onResponse(
                            call: Call<DetailHistoryResponse>,
                            response: Response<DetailHistoryResponse>
                        ) {
                            loadingDialog.dismiss()
                            if (response.isSuccessful) {
                                response.body()?.let { historyResponse ->
                                    Toast.makeText(this@DetailHistoryActivity, "Berhasil hapus history", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            } else {
                                val errorBody = response.errorBody()?.string()
                                errorBody?.let {
                                    try {
                                        val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                                        Toast.makeText(this@DetailHistoryActivity,errorResponse.message, Toast.LENGTH_SHORT).show()
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<DetailHistoryResponse>, t: Throwable) {
                            loadingDialog.dismiss()
                        }
                    })
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
            builder.create().show()
        }

        adapter = ExerciseAdapter()

        binding.exerciseList.layoutManager = LinearLayoutManager(this@DetailHistoryActivity)
        binding.exerciseList.adapter = adapter
        loadingDialog.show()
        val client = ApiConfig.getApiService(SharedPrefUtils.getAuthToken(this))
            .detailHistory(id)
        client.enqueue(object : Callback<DetailHistoryResponse> {
            override fun onResponse(
                call: Call<DetailHistoryResponse>,
                response: Response<DetailHistoryResponse>
            ) {
                loadingDialog.dismiss()
                if (response.isSuccessful) {
                    response.body()?.let { historyResponse ->
                        binding.tvDate.text = historyResponse.data.detailHistory.date
                        binding.tvDuration.text = historyResponse.data.detailHistory.duration
                        adapter.setList(historyResponse.data.detailHistory.exerciseHistories)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                            Toast.makeText(this@DetailHistoryActivity,errorResponse.message, Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<DetailHistoryResponse>, t: Throwable) {
                loadingDialog.dismiss()
            }
        })

    }

    fun shareImage(bitmap: Bitmap) {
        val file = File(this.cacheDir, "image.png")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        val uri = FileProvider.getUriForFile(this,"${packageName}.provider", file)

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/png"
        }
        startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }
}
