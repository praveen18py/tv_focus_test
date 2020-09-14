package com.example.sampletvfocustest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_page_recycler_row.view.*

class PageRecyclerAdapter : RecyclerView.Adapter<PageRecyclerAdapter.PageRecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PageRecyclerViewHolder =
        PageRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_page_recycler_row, parent, false)
        )

    override fun getItemCount() = 10

    override fun onBindViewHolder(
        holder: PageRecyclerViewHolder,
        position: Int
    ) {
        val itemAdapter = ArrayObjectAdapter(HorizontalGridPresenter())
        itemAdapter.add(listOf(""))
        itemAdapter.add(listOf(""))
        itemAdapter.add(listOf(""))
        itemAdapter.add(listOf(""))
        itemAdapter.add(listOf(""))
        itemAdapter.add(listOf(""))
        itemAdapter.add(listOf(""))

        // val horizontalGridPresenter = listOf(HorizontalGridPresenter())
        val hgv = holder.itemView.hgv

        val arrayObjectAdapter = ArrayObjectAdapter(ListRowPresenter())
        val itemBridgeAdapter = ItemBridgeAdapter(arrayObjectAdapter)

        arrayObjectAdapter.setItems(
            listOf(ListRow(getHeader()[position], itemAdapter)),
            listRowAdapterDiffCallback
        )

        val itemView = holder.itemView.mainFrame
        if (position == 9) {
            itemView.setPadding(
                0, 0, 0,
                convertDpToPixels(
                    150f,
                    itemView.context
                ).toInt()
            )
        } else {
            itemView.setPadding(0, 0, 0, 0)
        }

        hgv.adapter = itemBridgeAdapter
    }

    private fun getHeader(): List<HeaderItem> =
        listOf(
            HeaderItem("Rail Title 1"),
            HeaderItem("Rail Title 2"),
            HeaderItem("Rail Title 3"),
            HeaderItem("Rail Title 4"),
            HeaderItem("Rail Title 5"),
            HeaderItem("Rail Title 6"),
            HeaderItem("Rail Title 7"),
            HeaderItem("Rail Title 8"),
            HeaderItem("Rail Title 9"),
            HeaderItem("Rail Title 10")
        )


    inner class PageRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}

internal val listRowAdapterDiffCallback = object : DiffCallback<ListRow>() {
    override fun areItemsTheSame(
        oldItem: ListRow,
        newItem: ListRow
    ) = checkItems(oldItem, newItem)

    override fun areContentsTheSame(
        oldItem: ListRow,
        newItem: ListRow
    ) = checkItems(oldItem, newItem)

    private fun checkItems(oldItem: ListRow, newItem: ListRow): Boolean {
        return newItem.adapter.size() == oldItem.adapter.size()
                && adapterItemsTheSame(newItem.adapter, oldItem.adapter)
    }

    private fun adapterItemsTheSame(adapter1: ObjectAdapter, adapter2: ObjectAdapter): Boolean {
        for (i in 0 until adapter1.size()) {
            if (adapter1[i] != adapter2[i]) return false
        }
        return true
    }
}
