package com.satria.gymer.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
