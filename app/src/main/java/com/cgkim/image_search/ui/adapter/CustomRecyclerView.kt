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
import com.cgkim.image_search.data.ImageDocument
import com.cgkim.image_search.ui.ImagePopupActivity


class CustomRecyclerView(context: Context, documents: ArrayList<ImageDocument>?) :
    RecyclerView.Adapter<CustomRecyclerView.ViewHolder>() {
    private var glide: RequestManager? = null
    private var mDocuments: ArrayList<ImageDocument>? = documents

    init {
        glide = Glide.with(context)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imageView: ImageView? = itemView.findViewById(R.id.image)

        fun bind(item: ImageDocument?) {
            glide?.load(item?.thumbnail_url)
                ?.placeholder(R.mipmap.ic_launcher)
                ?.fitCenter()
                ?.into(imageView!!)
            imageView?.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, ImagePopupActivity::class.java).apply {
                    putExtra("data", item)
                }
                context.startActivity(intent)
            }
        }
    }

    private fun getItem(p0: Int): ImageDocument? {
        return if (mDocuments != null)
            mDocuments!![p0]
        else
            null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.gridview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return mDocuments?.size ?: 0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    fun resetItems() {
        mDocuments = null
    }

    fun addItems(arrayList: ArrayList<ImageDocument>?) {
        if (mDocuments == null) mDocuments = ArrayList()

        if (arrayList == null) {
            notifyDataSetChanged()
        } else {
            for (document: ImageDocument in arrayList) {
                mDocuments?.add(document)
            }
        }
        notifyDataSetChanged()
    }
}