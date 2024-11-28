package cn.isif.reviewandroid.recyclerView

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Orientation

class FixedItemLayoutManager(context: Context, @Orientation orientation: Int,val limit: Int = 0) :
    LinearLayoutManager(context, orientation, false) {
    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        if (itemCount > limit) {
            var realHeight = 0
            var realWidth = 0
            for (i in 0..limit) {
                val item = recycler.getViewForPosition(i)
                val measureHeight = item.measuredHeight
                val measureWidth = item.measuredWidth
                realWidth = if (measureWidth > realWidth)  measureWidth else realWidth
                realHeight += measureHeight
            }
            setMeasuredDimension(realWidth, realHeight)
        }
        state.itemCount

        super.onMeasure(recycler, state, widthSpec, heightSpec)
    }
}