package com.haolu.swipeandundo.view.helpers

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * This [RecyclerView.ItemDecoration] gives [RecyclerView.ViewHolder] a shrinking animation by
 * drawing a divider to fill the space when the item is "swiped away". This decoration works in
 * tandem with [SwipeRemoveActionCallback].
 *
 * @param background The item decoration (divider)
 * @param icon The icon seen above the background
 */
class SwipeRemoveItemDecoration(val background: ColorDrawable,
                                val icon: Drawable?) : RecyclerView.ItemDecoration() {
    // Used to check if item is being removed or added back to add divider or not
    private var itemCount = 0

    enum class SwipeDirection {
        LEFT,
        RIGHT
    }

    private var swipeDirection: SwipeDirection? = null

    // This is called when the item is swiped away
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        // Bounds of the divider
        val left = 0
        val right = parent.width
        var top = 0
        var bottom = 0

        // Items above or below the removed item
        var firstItemMovingUp: View? = null
        var lastItemComingDown: View? = null

        // Check if RecyclerView animation is running
        if (parent.isAnimating) {
            // Draw if the item is being removed only
            if (state.itemCount <= itemCount || itemCount == 0) {
                itemCount = state.itemCount
                // Loop for all itemViews in the RecyclerView
                for (i in 0 until parent.childCount) {
                    val child = parent.getChildAt(i)
                    // The remaining list below the removed item is being pushed up
                    if (child.translationY > 0) {
                        // Only need the first item in the remaining list moving up
                        if (firstItemMovingUp == null) firstItemMovingUp = child
                    }
                    // The remaining list above the removed item is being pushed down
                    else if (child.translationY < 0) {
                        lastItemComingDown = child
                    }

                    // Check item view is swiping left or right
                    if (child.translationX < 0) {
                        swipeDirection = SwipeDirection.LEFT
                    } else if (child.translationX > 0) {
                        swipeDirection = SwipeDirection.RIGHT
                    }
                }
                // Pinching, list below is pushed up and list above is pushed down
                if (firstItemMovingUp != null && lastItemComingDown != null) {
                    top = lastItemComingDown.bottom + lastItemComingDown.translationY.toInt()
                    bottom = firstItemMovingUp.top + firstItemMovingUp.translationY.toInt()
                }
                // Slide up, list below is pushed up (translationY is the view moving up)
                else if (firstItemMovingUp != null) {
                    top = firstItemMovingUp.top
                    bottom = firstItemMovingUp.top + firstItemMovingUp.translationY.toInt()

                }
                // Slide down, list above is pushed down (translationY is the view moving down)
                else if (lastItemComingDown != null) {
                    top = lastItemComingDown.bottom + lastItemComingDown.translationY.toInt()
                    bottom = lastItemComingDown.bottom
                }

                // Draw divider
                background.setBounds(left, top, right, bottom)
                background.draw(c)

                // Draw icon
                if (icon != null && swipeDirection != null) {
                    when (swipeDirection) {
                        SwipeDirection.LEFT -> {
                            val height = bottom - top
                            val iconMargin = icon.intrinsicHeight
                            val iconTop = top + (height - icon.intrinsicHeight) / 2
                            val iconBottom = iconTop + icon.intrinsicHeight
                            val iconLeft = right - iconMargin - icon.intrinsicWidth
                            val iconRight = right - iconMargin
                            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        }
                        SwipeDirection.RIGHT -> {
                            val height = bottom - top
                            val iconMargin = icon.intrinsicHeight
                            val iconTop = top + (height - icon.intrinsicHeight) / 2
                            val iconBottom = iconTop + icon.intrinsicHeight
                            val iconLeft = left + iconMargin
                            val iconRight = left + iconMargin + icon.intrinsicHeight
                            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        }
                    }
                }
                icon?.draw(c)
            }
        }
    }
}
