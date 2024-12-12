package com.satria.gymer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satria.gymer.ui.model.Exercise
import com.satria.gymer.databinding.ItemExerciseBinding

class ExerciseAdapter(private val exerciseList: List<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exerciseList[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exerciseList.size

    inner class ExerciseViewHolder(private val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.tvExerciseName.text = exercise.name
            binding.tvExerciseDate.text = exercise.date
            binding.tvExerciseDuration.text = exercise.duration
            binding.tvExerciseSet.text = exercise.set
            binding.tvExerciseWeight.text = exercise.weight
        }
    }
}
