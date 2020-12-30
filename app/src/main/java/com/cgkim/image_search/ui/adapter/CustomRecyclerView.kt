package com.cgkim.image_search.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.cgkim.image_search.R
import com.cgkim.image_search.data.ImageItem
import com.cgkim.image_search.ui.ImagePopupActivity


class CustomRecyclerView(context: Context, items: ArrayList<ImageItem>?) :
    RecyclerView.Adapter<CustomRecyclerView.ViewHolder>() {
    private var glide: RequestManager? = null
    private var mItems: ArrayList<ImageItem>? = items

    init {
        glide = Glide.with(context)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView? = itemView.findViewById(R.id.image)
    }

    private fun getItem(p0: Int): ImageItem? {
        return if (mItems != null)
            mItems!![p0]
        else
            null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.gridview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ImageItem? = getItem(position)

        glide?.load(item?.thumbnailUrl)
            ?.placeholder(R.mipmap.ic_launcher)
            ?.fitCenter()
            ?.into(holder.imageView!!)
        holder.imageView?.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ImagePopupActivity::class.java).apply {
                putExtra("data", item)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mItems?.size ?: 0
    }

    override fun getItemId(p0: Int): Long {
        return if (mItems != null)
            mItems!![p0].idx
        else 0
    }

    fun resetItems() {
        mItems = null
    }

    fun addItems(arrayList: ArrayList<ImageItem>?) {
        if (mItems == null) mItems = ArrayList()

        if (arrayList == null) {
            notifyDataSetChanged()
        } else {
            for (item: ImageItem in arrayList) {
                mItems?.add(item)
            }
        }
        notifyDataSetChanged()
    }
}