package apps.esampaio.com.comacerto.view.meals.register.adapter

import android.content.Context
import android.util.Log
import apps.esampaio.com.comacerto.core.entity.Feeling

class FeelingsListRecyclerViewAdapter(context: Context) : ImageRecyclerViewAdapter(context) {

    var onFeelingSelectedListener : ((Feeling) -> Unit)? = null
    //to remove Feeling.None
    var feelings = Feeling.values().drop(1)

    override fun onBindViewHolder(imageRecyclerViewAdapterViewHolder: ImageRecyclerViewAdapter.ImageRecyclerViewAdapterViewHolder, i: Int) {
        super.onBindViewHolder(imageRecyclerViewAdapterViewHolder, i)
        val feeling = feelings[i]
        imageRecyclerViewAdapterViewHolder.itemImage!!.setImageDrawable( feeling.getImage(context))
        imageRecyclerViewAdapterViewHolder.itemName!!.text = feeling.getName(context)
    }

    override fun getItemCount(): Int {
        return feelings.size
    }

    override fun itemSelectedAtPosition(position:Int){
        onFeelingSelectedListener?.invoke(feelings[position])
        Log.d("FeelingsAdapter", "positions is ${position}")
    }
}
