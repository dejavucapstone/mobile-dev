package com.satria.gymer.ui.fragment.plan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.satria.gymer.R
import com.satria.gymer.data.model.ErrorResponse
import com.satria.gymer.data.model.history.HistoryData
import com.satria.gymer.data.model.history.HistoryResponse
import com.satria.gymer.data.network.ApiConfig
import com.satria.gymer.databinding.ActivityLoginBinding
import com.satria.gymer.databinding.FragmentHomeBinding
import com.satria.gymer.databinding.FragmentPlanBinding
import com.satria.gymer.ui.activity.CameraXActivity
import com.satria.gymer.ui.adapter.ExerciseAdapter
import com.satria.gymer.ui.model.Exercise
import com.satria.gymer.ui.activity.DetailHistoryActivity
import com.satria.gymer.ui.adapter.HistoryAdapter
import com.satria.gymer.ui.model.HistoryItem
import com.satria.gymer.utils.LoadingDialogUtils
import com.satria.gymer.utils.SharedPrefUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanFragment : Fragment(R.layout.fragment_plan) {

<<<<<<< HEAD
    private var historyList: List<HistoryData> = listOf()
    private lateinit var adapter: HistoryAdapter
    private lateinit var binding: FragmentPlanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPlanBinding.inflate(inflater, container, false)

        // Set up RecyclerView
        binding.recyclerViewExercise.layoutManager = LinearLayoutManager(context)

        // Populate RecyclerView with some sample data

        adapter = HistoryAdapter({ historyData:HistoryData ->
            onExerciseClick(historyData)
        })
        binding.recyclerViewExercise.adapter = adapter

        binding.btnAdd.setOnClickListener {
            checkCameraPermission()
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val loadingDialog = LoadingDialogUtils(requireContext())
        loadingDialog.show()

        val client = ApiConfig.getApiService(SharedPrefUtils.getAuthToken(requireContext()))
            .getHistory()
        client.enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                loadingDialog.dismiss()
                if (response.isSuccessful) {
                    response.body()?.let { historyResponse ->
                        adapter.setList(historyResponse.data)
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

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                loadingDialog.dismiss()
            }
        })
    }

    private fun onExerciseClick(historyData: HistoryData) {
        val intent = Intent(requireContext(), DetailHistoryActivity::class.java)
        intent.putExtra("id", historyData.historyId.toString())
        startActivity(intent)
    }

    // Sample function to get some exercise data


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
=======
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
}
