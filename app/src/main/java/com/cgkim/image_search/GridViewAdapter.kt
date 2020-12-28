package com.cgkim.image_search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GridViewAdapter(context: Context, arrayList: ArrayList<ImageItem>?) : BaseAdapter() {
    private var mItems: ArrayList<ImageItem>? = arrayList
    private var glide: RequestManager? = null

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

    fun resetItems() {
        mItems = null
    }

    fun addItems(arrayList: ArrayList<ImageItem>) {
        if (mItems == null) mItems = ArrayList()

        for (item: ImageItem in arrayList) {
            mItems?.add(item)
        }
        CoroutineScope(Dispatchers.Main).launch {
            notifyDataSetChanged()
        }
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
            println("item : $item")

            val intent = Intent(parent.context, ImageActivity::class.java).apply {
                putExtra("data", item)
            }
            parent.context.startActivity(intent)
        }
        return view!!
    }

    inner class ViewHolder(context: Context) : View(context) {
        var imageView: ImageView? = null
    }
}