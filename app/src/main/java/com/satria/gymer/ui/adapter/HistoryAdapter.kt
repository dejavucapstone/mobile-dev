package com.satria.gymer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satria.gymer.data.model.history.HistoryData
import com.satria.gymer.databinding.ItemHistoryBinding
import com.satria.gymer.ui.model.Exercise
import com.satria.gymer.ui.model.HistoryItem

class HistoryAdapter(
    private val onClick: (HistoryData) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var historyList: List<HistoryData> = arrayListOf()

    fun setList(historyList: List<HistoryData>){
        this.historyList = historyList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        // Inflate the item layout and get the binding object
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = historyList[position]
        holder.bind(historyItem)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(historyItem: HistoryData) {
            // Use the binding object to set values
            binding.historyDate.text = historyItem.detailHistory.date
            binding.historyDuration.text = historyItem.detailHistory.duration
            binding.root.setOnClickListener {
                onClick(historyItem)
            }
        }
    }
}
