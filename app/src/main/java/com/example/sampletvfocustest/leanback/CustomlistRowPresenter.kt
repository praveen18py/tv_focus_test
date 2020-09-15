package com.example.sampletvfocustest.leanback

import androidx.leanback.widget.*
import com.example.sampletvfocustest.R

class CustomListRowPresenter(
    focusZoomFactor: Int,
    useFocusDimmer: Boolean
) : ListRowPresenter(focusZoomFactor, useFocusDimmer) {

    override fun onRowViewSelected(holder: RowPresenter.ViewHolder?, selected: Boolean) {
        // This method is overridden so that spacing between header and row
        // should not be changed based on row focus
        dispatchItemSelectedListener(holder, selected)
    }

    override fun initializeRowViewHolder(holder: RowPresenter.ViewHolder?) {
        super.initializeRowViewHolder(holder)
        (holder?.view as? ListRowView)?.gridView?.let { listRowView ->
            listRowView.apply {
                windowAlignment = BaseGridView.WINDOW_ALIGN_LOW_EDGE
                windowAlignmentOffsetPercent = 0f
                windowAlignmentOffset = paddingStart
                itemAlignmentOffsetPercent = 0f
            }
            holder.view?.resources?.getDimensionPixelSize(R.dimen.row_padding_start_home)
                ?.let { startPadding ->
                    listRowView.setPadding(
                        startPadding,
                        listRowView.paddingTop,
                        listRowView.paddingEnd,
                        listRowView.paddingBottom
                    )
                }
        }
    }

    override fun isUsingDefaultListSelectEffect(): Boolean {
        return false
    }

    override fun isUsingDefaultShadow(): Boolean {
        return false
    }

}
