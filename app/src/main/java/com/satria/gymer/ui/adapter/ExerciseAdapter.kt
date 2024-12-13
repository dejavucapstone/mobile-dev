package com.satria.gymer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satria.gymer.data.model.history.ExerciseHistory
import com.satria.gymer.databinding.ItemExerciseBinding

class ExerciseAdapter(

) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    private var exerciseHistoryList: List<ExerciseHistory> = arrayListOf()

    fun setList(historyList: List<ExerciseHistory>){
        this.exerciseHistoryList = historyList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exerciseHistoryList[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exerciseHistoryList.size

    inner class ExerciseViewHolder(private val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exerciseHistory: ExerciseHistory) {
            binding.tvExerciseName.text = exerciseHistory.exerciseName
            for(set in exerciseHistory.sets){
                binding.llSetContainer.addView(TextView(binding.root.context).apply {
                    text = "${set.weight} Kg : ${set.repetitions}x"
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 0, 0, (10*binding.root.resources.displayMetrics.density).toInt()) // Set the margins here
                    }
                })
            }

        }
    }
}
