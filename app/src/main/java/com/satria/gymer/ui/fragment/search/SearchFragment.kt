package com.satria.gymer.ui.fragment.search

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.satria.gymer.network.ApiClient
import com.satria.gymer.network.ApiService
import com.satria.gymer.network.response.DataExercises
import com.satria.gymer.ui.adapter.ExerciseAdapter
import com.satria.gymer.databinding.FragmentSearchBinding
import com.satria.gymer.ui.activity.CameraXActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var apiService: ApiService
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Menggunakan View Binding
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        apiService = ApiClient.getClient().create(ApiService::class.java)

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        // Call API to get exercises
        apiService.getExercises().enqueue(object : Callback<List<DataExercises>> {
            override fun onResponse(
                call: Call<List<DataExercises>>,
                response: Response<List<DataExercises>>
            ) {
                if (response.isSuccessful) {
                    val exercises = response.body() ?: emptyList()
                    binding.recyclerView.adapter = ExerciseAdapter(exercises)
                }
            }

            override fun onFailure(call: Call<List<DataExercises>>, t: Throwable) {
                // Handle failure
            }
        })

        // Menangani klik pada tombol ic_scan
        binding.icScan.setOnClickListener {
            // Cek izin kamera
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
                // Intent untuk membuka CameraXActivity
                val intent = Intent(activity, CameraXActivity::class.java)
                startActivity(intent)
            } else {
                // Jika izin belum diberikan, minta izin
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(android.Manifest.permission.CAMERA), 10
                )
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Bersihkan binding untuk menghindari memory leak
    }
}
