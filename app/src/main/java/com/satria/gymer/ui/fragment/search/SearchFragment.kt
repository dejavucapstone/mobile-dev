package com.satria.gymer.ui.fragment.search

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
=======
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
<<<<<<< HEAD
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
=======
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.satria.gymer.ui.adapter.ExerciseAdapter
import com.satria.gymer.databinding.FragmentSearchBinding
import com.satria.gymer.ui.activity.CameraXActivity
import com.satria.gymer.ui.model.ExcerciseViewModel
import com.satria.gymer.network.response.DataItem
import com.satria.gymer.ui.activity.DetailExerciseActivity

class SearchFragment : Fragment() {

    private lateinit var excerciseViewModel: ExcerciseViewModel
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

<<<<<<< HEAD
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)

        adapter = ItemAdapter(
            onItemClick = { item ->
                openDetailActivity(item) // Pindah ke halaman detail saat item diklik
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
=======
        // Initialize ViewModel
        excerciseViewModel = ViewModelProvider(this).get(ExcerciseViewModel::class.java)

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        // Observe the exercise data
        excerciseViewModel.excerciseData.observe(viewLifecycleOwner, { exercises ->
            // Handle API response data
            if (exercises != null && exercises.isNotEmpty()) {
                Log.d("SearchFragment", "API Response: ${exercises.size} items received")
                binding.recyclerView.adapter = ExerciseAdapter(exercises) { exercise ->
                    // Handle item click here (open DetailExerciseActivity)
                    val intent = Intent(activity, DetailExerciseActivity::class.java)
                    intent.putExtra("EXERCISE_ID", exercise.idExercise) // Mengirim ID latihan
                    startActivity(intent)
                }
            } else {
                Log.d("SearchFragment", "No exercises found in API response")
                // Optionally show a message when no exercises are found
            }
        })

        // Observe loading state
        excerciseViewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            // Show or hide loading indicator based on isLoading
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE  // Show progress bar
            } else {
                binding.progressBar.visibility = View.GONE  // Hide progress bar
            }
        })

        // Observe error state
        excerciseViewModel.isError.observe(viewLifecycleOwner, { isError ->
            // Handle error state
            if (isError) {
                // Display error message
                Log.e("SearchFragment", "Error: ${excerciseViewModel.errorMessage}")
                // Optionally show error message to user
            }
        })

        // Handle camera scan button click
        binding.icScan.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(activity, CameraXActivity::class.java)
                startActivity(intent)
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(android.Manifest.permission.CAMERA), 10
                )
            }
        }
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3

        // Trigger API call to fetch data (e.g., search by category or general fetch)
        excerciseViewModel.getExcerciseData()

        return binding.root
    }

<<<<<<< HEAD
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
=======
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
    }
}
