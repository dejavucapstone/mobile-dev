package com.satria.gymer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satria.gymer.databinding.ItemListBinding
import com.satria.gymer.network.response.DataItem  // Use DataItem instead of DataExercises

class ItemAdapter(
    private val items: List<DataItem>,  // Use DataItem here
    private val onItemClick: (DataItem) -> Unit  // Function to handle item click
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataItem) {
            // Bind the properties of DataItem
            binding.itemTitle.text = item.namaExercise  // Using the property from DataItem
            binding.itemSubtitle.text = item.description // Using description from DataItem
            Glide.with(binding.itemIcon.context)
                .load(item.gambar)  // Use 'gambar' URL to load the image
                .into(binding.itemIcon)

            // If there's any other logic, like handling a favorite status, you can add it here
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
