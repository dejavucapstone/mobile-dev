package com.satria.gymer.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
<<<<<<< HEAD
import com.satria.gymer.data.model.history.ExerciseHistory
import com.satria.gymer.databinding.ItemExerciseBinding

class ExerciseAdapter(

) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    private var exerciseHistoryList: List<ExerciseHistory> = arrayListOf()

    fun setList(historyList: List<ExerciseHistory>){
        this.exerciseHistoryList = historyList
        notifyDataSetChanged()
=======
import com.bumptech.glide.Glide
import com.satria.gymer.databinding.ItemListBinding
import com.satria.gymer.network.response.DataItem
import com.satria.gymer.ui.activity.DetailExerciseActivity

class ExerciseAdapter(
    private val items: List<DataItem>,  // DataItem for exercise
    private val onItemClick: (DataItem) -> Unit  // Function for item click handling
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem) {
            // Bind data to UI elements
            binding.itemTitle.text = item.namaExercise  // Exercise name
            binding.itemSubtitle.text = item.description // Exercise description
            Glide.with(binding.itemIcon.context)
                .load(item.gambar)  // Image URL
                .into(binding.itemIcon)

            // Set click listener for the item
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
<<<<<<< HEAD
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
=======
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
}
