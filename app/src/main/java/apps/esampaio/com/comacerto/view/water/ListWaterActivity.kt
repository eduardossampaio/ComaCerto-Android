package apps.esampaio.com.comacerto.view.water

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.widget.LinearLayout
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.core.service.water.WaterIteractor
import apps.esampaio.com.comacerto.core.service.water.WaterService
import apps.esampaio.com.comacerto.view.water.adapter.ListWaterRVAdapter
import kotlinx.android.synthetic.main.activity_list_water.*

class ListWaterActivity : AppCompatActivity() {
    lateinit var waterIteractor: WaterIteractor

    companion object {
        val PARAM_WATER_LIST = "PARAM_WATER_LIST"
        fun createIntent(context: Context,waterList: ArrayList<Water>) : Intent{
            val intent = Intent(context,ListWaterActivity::class.java)
            intent.putParcelableArrayListExtra(PARAM_WATER_LIST,waterList)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_water)
        if(intent!=null) {
            val waterList = intent.extras.getSerializable(PARAM_WATER_LIST) as List<Water>
            list_water_rv.adapter = ListWaterRVAdapter(this, waterList.toMutableList())
            list_water_rv.layoutManager = LinearLayoutManager(this)

            val swipeHandler = object : SwipeToDeleteCallback(this) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                    val adapter = list_water_rv.adapter as ListWaterRVAdapter
                    val removedItem = adapter.removeAt(viewHolder.adapterPosition)
                    waterIteractor.onWaterRemoved(removedItem)
                }
            }
            waterIteractor = WaterService(this)
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(list_water_rv)
            setTitle(R.string.consumed_water)
        }
    }

}

/**
 * classe maluca que achei na internet para fazer o swipe to delete
 * link: https://medium.com/@kitek/recyclerview-swipe-to-delete-easier-than-you-thought-cff67ff5e5f6
 *
 */
abstract class SwipeToDeleteCallback(context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.icon_delete)!!
    private val intrinsicWidth = deleteIcon.intrinsicWidth
    private val intrinsicHeight = deleteIcon.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#f44336")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(
            c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        // Draw the red delete background
        background.color = backgroundColor
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(c)

        // Calculate position of delete icon
        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        // Draw the delete icon
        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}