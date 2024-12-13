package com.satria.gymer.ui.fragment.search

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.satria.gymer.R
import com.satria.gymer.data.model.ErrorResponse
import com.satria.gymer.data.model.exercise.Exercise
import com.satria.gymer.data.model.exercise.ExerciseResponse
import com.satria.gymer.data.model.history.HistoryResponse
import com.satria.gymer.data.network.ApiConfig
import com.satria.gymer.ui.activity.CameraXActivity
import com.satria.gymer.ui.activity.DetailExerciseActivity
import com.satria.gymer.ui.adapter.ItemAdapter
import com.satria.gymer.ui.model.Item
import com.satria.gymer.utils.LoadingDialogUtils
import com.satria.gymer.utils.SharedPrefUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openCameraActivity()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Izin kamera ditolak. Fitur ini tidak dapat digunakan.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private lateinit var adapter:ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)

        adapter = ItemAdapter(
            onItemClick = { item ->
                openDetailActivity(item) // Pindah ke halaman detail saat item diklik
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        view.findViewById<ImageView>(R.id.ic_scan).setOnClickListener {
            checkCameraPermission()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val loadingDialog = LoadingDialogUtils(requireContext())
        loadingDialog.show()

        val client = ApiConfig.getApiService(SharedPrefUtils.getAuthToken(requireContext()))
            .getExercises()
        client.enqueue(object : Callback<ExerciseResponse> {
            override fun onResponse(
                call: Call<ExerciseResponse>,
                response: Response<ExerciseResponse>
            ) {
                loadingDialog.dismiss()
                if (response.isSuccessful) {
                    response.body()?.let { exerciseResponse ->
                        adapter.setList(exerciseResponse.data)
                        Log.d("TAG", "onResponse: "+exerciseResponse.data.size)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                            Toast.makeText(requireContext(),errorResponse.message,Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ExerciseResponse>, t: Throwable) {
                loadingDialog.dismiss()
            }
        })
    }

    private fun openDetailActivity(exercise: Exercise) {
        val intent = Intent(requireContext(), DetailExerciseActivity::class.java).apply {
            putExtra("id", exercise.idExercise.toString())
        }
        startActivity(intent)
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted
                openCameraActivity()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // Show explanation dialog and request permission again
                Toast.makeText(
                    requireContext(),
                    "Izin kamera diperlukan untuk menggunakan fitur ini.",
                    Toast.LENGTH_SHORT
                ).show()
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }

            else -> {
                // Request permission
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCameraActivity() {
        val intent = Intent(requireContext(), CameraXActivity::class.java)
        startActivity(intent)
    }
}
