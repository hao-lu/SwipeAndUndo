package com.haolu.swipeandundo

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar

/**
 * Material Design 2 Snackbar style
 */
fun Snackbar.config(context: Context) {
    val params = this.view.layoutParams as ViewGroup.MarginLayoutParams
    val margin = context.resources.getDimension(R.dimen.snackbar_margins).toInt()
    params.setMargins(margin, margin, margin, margin)
    this.view.layoutParams = params
    this.view.background = context.getDrawable(R.drawable.bg_snackbar)
    ViewCompat.setElevation(this.view, 6f)
}
