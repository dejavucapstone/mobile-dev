package com.satria.gymer.ui.fragment.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satria.gymer.R
import com.satria.gymer.ui.adapter.ExerciseAdapter
import com.satria.gymer.ui.model.Exercise

class PlanFragment : Fragment(R.layout.fragment_plan) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var exerciseAdapter: ExerciseAdapter
    private var exerciseList: List<Exercise> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_plan, container, false)

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_exercise)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Populate RecyclerView with some sample data
        exerciseList = getExerciseData()
        exerciseAdapter = ExerciseAdapter(exerciseList)
        recyclerView.adapter = exerciseAdapter

        return view
    }

    // Sample function to get some exercise data
    private fun getExerciseData(): List<Exercise> {
        return listOf(
            Exercise("Latihan Jumat", "13 November 2024", "1 menit", "2", "180kg"),
            Exercise("PUSH DAY!!!!!", "14 November 2024", "5 menit", "7", "30kg")
        )
    }
}
