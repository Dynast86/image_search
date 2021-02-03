package com.cgkim.image_search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cgkim.image_search.databinding.ViewSearchAdapterItemBinding
import com.cgkim.image_search.extension.ActionHandler
import com.cgkim.image_search.model.ImageDocument

class SearchListAdapter : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    var itemList = mutableListOf<ImageDocument>()

    inner class ViewHolder(private var binding: ViewSearchAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(document: ImageDocument) {
            binding.apply {
                item = document
                executePendingBindings()
                handler = ActionHandler()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewSearchAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.size
}