package com.satria.gymer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satria.gymer.R
import com.satria.gymer.data.model.exercise.Exercise
import com.satria.gymer.databinding.ItemListBinding
import com.satria.gymer.ui.model.Item

class ItemAdapter(
    private val onItemClick: (Exercise) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var items: List<Exercise> = arrayListOf()

    fun setList(items:List<Exercise>){
        this.items = items
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
