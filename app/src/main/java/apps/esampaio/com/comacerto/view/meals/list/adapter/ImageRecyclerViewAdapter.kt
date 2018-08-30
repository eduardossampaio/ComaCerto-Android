package apps.esampaio.com.comacerto.view.meals.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import apps.esampaio.com.comacerto.R

abstract class ImageRecyclerViewAdapter(val context:Context) : RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageRecyclerViewAdapterViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ImageRecyclerViewAdapterViewHolder {
        val baseView = LayoutInflater.from(context).inflate(R.layout.image_collection_recycler_view_item,null,false)
        return ImageRecyclerViewAdapterViewHolder(baseView)
    }

    class ImageRecyclerViewAdapterViewHolder(view:View) : RecyclerView.ViewHolder(view) {
        var itemImage:ImageView? = null
        var itemName:TextView? = null
        init {
            itemImage = view.findViewById(R.id.item_image)
            itemName = view.findViewById(R.id.item_name)
        }
    }
}