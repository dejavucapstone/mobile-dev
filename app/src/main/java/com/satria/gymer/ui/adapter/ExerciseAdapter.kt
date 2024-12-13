package com.satria.gymer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satria.gymer.databinding.ItemListBinding
import com.satria.gymer.network.response.DataExercises

class ExerciseAdapter(private val exercises: List<DataExercises>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exercises.size

    class ExerciseViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: DataExercises) {
            binding.itemTitle.text = exercise.name
            binding.itemSubtitle.text = exercise.description
            // Jika Anda memiliki ikon atau gambar, Anda bisa menambahkannya di sini
            // binding.itemIcon.setImageResource(exercise.icon)
        }
    }
}
