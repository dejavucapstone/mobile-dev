package com.satria.gymer.ui.fragment.search

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

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

        // Trigger API call to fetch data (e.g., search by category or general fetch)
        excerciseViewModel.getExcerciseData()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
