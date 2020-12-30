package com.cgkim.image_search.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.cgkim.image_search.R
import com.cgkim.image_search.data.ImageItem
import com.cgkim.image_search.ui.ImagePopupActivity

class GridViewAdapter(context: Context, items: ArrayList<ImageItem>?) : BaseAdapter() {
    private var glide: RequestManager? = null
    private var mItems: ArrayList<ImageItem>? = items

    init {
        glide = Glide.with(context)
    }

    override fun getCount(): Int {
        return mItems?.size ?: 0
    }

    override fun getItem(p0: Int): ImageItem? {
        return if (mItems != null)
            mItems!![p0]
        else
            null
    }

    override fun getItemId(p0: Int): Long {
        return if (mItems != null)
            mItems!![p0].idx
        else 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: View
        if (view == null) {
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.gridview_item, parent, false)
            holder = ViewHolder(parent.context)
            holder.imageView = view?.findViewById(R.id.image)
            view?.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val item = getItem(position)

        glide?.load(item?.thumbnailUrl)
            ?.placeholder(R.mipmap.ic_launcher)
            ?.fitCenter()
            ?.into(holder.imageView!!)
        holder.imageView?.setOnClickListener {
            val intent = Intent(parent.context, ImagePopupActivity::class.java).apply {
                putExtra("data", item)
            }
            parent.context.startActivity(intent)
        }
        return view!!
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

    inner class ViewHolder(context: Context) : View(context) {
        var imageView: ImageView? = null
    }
}