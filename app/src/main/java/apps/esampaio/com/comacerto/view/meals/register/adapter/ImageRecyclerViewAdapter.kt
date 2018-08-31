package apps.esampaio.com.comacerto.view.meals.register.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import apps.esampaio.com.comacerto.R

abstract class ImageRecyclerViewAdapter(val context:Context): RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageRecyclerViewAdapterViewHolder>(){

    var selectedItem = 0

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ImageRecyclerViewAdapterViewHolder {
        val baseView = LayoutInflater.from(context).inflate(R.layout.image_collection_recycler_view_item,null,false)
        return ImageRecyclerViewAdapterViewHolder(baseView,this)
    }

    override fun onBindViewHolder(viewHolder: ImageRecyclerViewAdapterViewHolder, position: Int) {
        if (selectedItem == position){
            viewHolder.markAsSelected()
        }else{
            viewHolder.markAsDeselected()
        }
    }

    class ImageRecyclerViewAdapterViewHolder(view:View,adapter: ImageRecyclerViewAdapter) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var itemImage:ImageView? = null
        var itemName:TextView? = null
        var adapter:ImageRecyclerViewAdapter
        init {
            itemImage = view.findViewById(R.id.item_image)
            itemName = view.findViewById(R.id.item_name)
            itemView.setOnClickListener(this)
            this.adapter = adapter
        }

        override fun onClick(p0: View?) {
            adapter.selectedItem = adapterPosition
            adapter.notifyDataSetChanged()

        }

        fun markAsSelected() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.item_selected_color))
        }
        fun markAsDeselected(){
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.item_deselected_color))
        }
    }
}