package com.satria.gymer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
<<<<<<< HEAD
import com.satria.gymer.R
import com.satria.gymer.data.model.exercise.Exercise
=======
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
import com.satria.gymer.databinding.ItemListBinding
import com.satria.gymer.network.response.DataItem  // Use DataItem instead of DataExercises

class ItemAdapter(
<<<<<<< HEAD
    private val onItemClick: (Exercise) -> Unit
=======
    private val items: List<DataItem>,  // Use DataItem here
    private val onItemClick: (DataItem) -> Unit  // Function to handle item click
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var items: List<Exercise> = arrayListOf()

    fun setList(items:List<Exercise>){
        this.items = items
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

<<<<<<< HEAD
        fun bind(item: Exercise) {
            Glide.with(binding.root.context).load(item.imageUrl)
            binding.itemIcon
            binding.itemTitle.text = item.exerciseName
            binding.itemSubtitle.text = item.bodyPart

            // Handle click on the item
            binding.root.setOnClickListener {
                onItemClick(item)
            }

            // Handle click on the favorite icon
//            binding.itemFavorite.setOnClickListener {
//                // Toggle favorite state
//                item.isFavorite = !item.isFavorite
//                binding.itemFavorite.setImageResource(
//                    if (item.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_star_outline
//                )
//                notifyItemChanged(adapterPosition)
//            }
=======
        fun bind(item: DataItem) {
            // Bind the properties of DataItem
            binding.itemTitle.text = item.namaExercise  // Using the property from DataItem
            binding.itemSubtitle.text = item.description // Using description from DataItem
            Glide.with(binding.itemIcon.context)
                .load(item.gambar)  // Use 'gambar' URL to load the image
                .into(binding.itemIcon)

            // If there's any other logic, like handling a favorite status, you can add it here
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
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
