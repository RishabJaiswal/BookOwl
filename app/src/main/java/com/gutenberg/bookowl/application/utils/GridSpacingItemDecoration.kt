package com.gutenberg.bookowl.application.utils


import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class GridSpacingItemDecoration(private val mSpanCount: Int, itemSize: Int) :
    RecyclerView.ItemDecoration() {

    private val mItemSize: Float = itemSize.toFloat()

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        val column = position % mSpanCount
        val parentWidth = parent.width
        val spacing = (parentWidth - mItemSize * mSpanCount).toInt() / (mSpanCount + 1)
        outRect.left = spacing - column * spacing / mSpanCount
        outRect.right = (column + 1) * spacing / mSpanCount

        if (position < mSpanCount) {
            outRect.top = spacing
        }
        outRect.bottom = spacing
    }
}