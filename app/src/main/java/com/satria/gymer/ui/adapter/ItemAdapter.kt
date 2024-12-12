package com.satria.gymer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satria.gymer.R
import com.satria.gymer.databinding.ItemListBinding
import com.satria.gymer.ui.model.Item

class ItemAdapter(
    private val items: List<Item>,
    private val onItemClick: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            // Bind the data to the views using ViewBinding
            binding.itemIcon.setImageResource(item.icon)
            binding.itemTitle.text = item.title
            binding.itemSubtitle.text = item.subtitle
            binding.itemFavorite.setImageResource(
                if (item.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_star_outline
            )

            // Make the favorite icon clickable
            binding.itemFavorite.isClickable = true
            binding.itemFavorite.isFocusable = true

            // Handle click on the favorite icon
            binding.itemFavorite.setOnClickListener {
                // Toggle the favorite state
                item.isFavorite = !item.isFavorite
                binding.itemFavorite.setImageResource(
                    if (item.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_star_outline
                )
                // Call the onItemClick function to notify the listener
                onItemClick(item)
            }
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
