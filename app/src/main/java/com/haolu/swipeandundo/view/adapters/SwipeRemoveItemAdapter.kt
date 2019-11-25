package com.haolu.swipeandundo.view.adapters

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.haolu.swipeandundo.view.helpers.SwipeRemoveActionListener
import com.haolu.swipeandundo.view.helpers.UndoActionListener

/**
 * This fixes the issue with the last item not "animating"
 */
abstract class SwipeRemoveItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    SwipeRemoveActionListener,
    UndoActionListener {

    companion object {
        const val TYPE_FOOTER = 0
        const val TYPE_ITEM = 1
    }

    var listSize: Int = 0

    override fun getItemViewType(position: Int): Int {
        return if (position == listSize) TYPE_FOOTER else TYPE_ITEM
    }

    // Add one to size for empty footer to get animation for last elements
    override fun getItemCount(): Int {
        return if (listSize >= 0) listSize + 1 else 0
    }

    fun createFooterViewHolder(context: Context): FooterViewHolder {
        val view = LinearLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 20)
        }
        return FooterViewHolder(view)
    }

    override fun removeItem(position: Int) {
        --listSize
        notifyItemRemoved(position)
    }

    override fun putBackItem(position: Int) {
        ++listSize
        notifyItemInserted(position)
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
