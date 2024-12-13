package com.satria.gymer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satria.gymer.R
import com.satria.gymer.databinding.ItemListBinding
import com.satria.gymer.network.response.DataExercises
import com.satria.gymer.ui.model.Item

class ItemAdapter(
    private val items: List<DataExercises>,  // Gunakan DataExercises di sini
    private val onItemClick: (DataExercises) -> Unit  // Fungsi klik untuk Item
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataExercises) {
            binding.itemTitle.text = item.name
            binding.itemSubtitle.text = item.description
            // Menggunakan Glide atau Picasso untuk memuat gambar
            Glide.with(binding.itemIcon.context)
                .load(item.imageUrl)  // Sesuaikan dengan URL gambar atau resource
                .into(binding.itemIcon)

            // Favorite icon action
            binding.itemFavorite.setImageResource(
                if (item.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_star_outline
            )

            binding.itemFavorite.setOnClickListener {
                item.isFavorite = !item.isFavorite
                binding.itemFavorite.setImageResource(
                    if (item.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_star_outline
                )
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

