package com.satria.gymer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satria.gymer.R
import com.satria.gymer.databinding.ItemAnalysisBinding
import com.satria.gymer.ui.model.AnalysisItem

class AnalysisAdapter(
    private val analysisList: MutableList<AnalysisItem>,
    private val onItemClick: (AnalysisItem) -> Unit
) : RecyclerView.Adapter<AnalysisAdapter.AnalysisViewHolder>() {

    inner class AnalysisViewHolder(private val binding: ItemAnalysisBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(analysisItem: AnalysisItem) {
            binding.itemTitle.text = analysisItem.title
            binding.itemSubtitle.text = analysisItem.subtitle
            binding.itemIcon.setImageResource(analysisItem.iconResId)
            binding.itemFavorite.setImageResource(
                if (analysisItem.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_star_outline
            )

            binding.itemFavorite.setOnClickListener {
                analysisItem.isFavorite = !analysisItem.isFavorite
                binding.itemFavorite.setImageResource(
                    if (analysisItem.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_star_outline
                )
                notifyItemChanged(adapterPosition)
            }

            itemView.setOnClickListener {
                onItemClick(analysisItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalysisViewHolder {
        val binding =
            ItemAnalysisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnalysisViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnalysisViewHolder, position: Int) {
        holder.bind(analysisList[position])
    }

    override fun getItemCount(): Int = analysisList.size
}