package com.haolu.swipeandundo.view.helpers

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.haolu.swipeandundo.view.adapters.SwipeRemoveItemAdapter

/**
 * This class extends [ItemTouchHelper.SimpleCallback] giving the attached RecyclerView the ability
 * have its "items" swipe left and/or right.
 *
 * The "shrinking" animation when the item is "swiped away" is done by [SwipeRemoveItemDecoration].
 *
 * @param background The background color seen when the item is swiped away
 * @param icon The icon seen when the item is swiped away
 * @param swipeRemoveActionListenerList List of listeners for removing an item
 * @param swipeDirection [ItemTouchHelper.LEFT] and/or [ItemTouchHelper.RIGHT]
 */
class SwipeRemoveActionCallback(private val background: ColorDrawable,
                                private val icon: Drawable?,
                                private val swipeRemoveActionListenerList: ArrayList<SwipeRemoveActionListener>,
                                swipeDirection: Int) : ItemTouchHelper.SimpleCallback(0, swipeDirection) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        TODO("Implement method for drag & drop")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        swipeRemoveActionListenerList.forEach { it.removeItem(position) }
    }

    /**
     * Do not allow swipe if the ViewHolder is of type FooterViewHolder
     */
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        if (viewHolder is SwipeRemoveItemAdapter.FooterViewHolder) return 0
        return super.getMovementFlags(recyclerView, viewHolder)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        // Check when item is added back to an empty list
        if (viewHolder.adapterPosition == -1) return

        val itemView = viewHolder.itemView

        // Swipe left
        if (dX < 0) {
            // Calculate position of icon
            if (icon != null) {
                val iconMargin = icon.intrinsicHeight
                val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
                val iconBottom = iconTop + icon.intrinsicHeight
                val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                // Set bounds for icon to where to draw
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            }
            // Set bounds for background to where to draw
            background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        }
        // Swipe right
        else if (dX > 0) {
            // Calculate position of icon
            if (icon != null) {
                val iconMargin = icon.intrinsicHeight
                val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
                val iconBottom = iconTop + icon.intrinsicHeight
                val iconLeft = itemView.left + iconMargin
                val iconRight = itemView.left + iconMargin + icon.intrinsicWidth
                // Set bounds for icon to where to draw
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            }
            // Set bounds for background  to where to draw
            background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
        }
        else {
            background.setBounds(0, 0, 0,0)
            icon?.setBounds(0, 0, 0, 0)
        }

        // Draw
        background.draw(c)
        icon?.draw(c)
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}

/**
 * This is a contract for implementing [SwipeRemoveActionCallback] to gain swipe feature.
 */
interface SwipeRemoveActionListener {
    fun removeItem(position: Int)
}

/**
 * This is a contract to get the item back in the list.
 */
interface UndoActionListener {
    fun putBackItem(position: Int)
}
