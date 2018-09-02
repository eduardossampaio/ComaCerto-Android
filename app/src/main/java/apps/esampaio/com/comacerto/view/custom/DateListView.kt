package apps.esampaio.com.comacerto.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import apps.esampaio.com.comacerto.R

class DateListView : RelativeLayout{

    constructor(context: Context): super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) :super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int):  super(context, attrs, defStyle) {
        initView()
    }

    fun initView(){
        inflate(getContext(), R.layout.date_list_view, this);
    }


}