package com.haolu.swipeandundo.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.haolu.swipeandundo.BR
import com.haolu.swipeandundo.R
import com.haolu.swipeandundo.model.Email
import com.haolu.swipeandundo.view.helpers.SwipeRemoveActionListener
import com.haolu.swipeandundo.view.helpers.UndoActionListener

class ItemViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    SwipeRemoveActionListener,
    UndoActionListener {

    private val TYPE_FOOTER = 0
    private val TYPE_ITEM = 1

    private var list: MutableList<Email>? = null

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ViewDataBinding? = DataBindingUtil.bind(view)
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.binding?.setVariable(BR.email, list!![position])
            holder.binding?.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_FOOTER -> {
                val view = LinearLayout(parent.context).apply {
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 20)
                }
                FooterViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_email_content, parent, false)
                ItemViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list?.size!!) TYPE_FOOTER else TYPE_ITEM
    }

    // Add one to size for empty footer to get animation for last elements
    override fun getItemCount(): Int {
        return if (list != null) list!!.size + 1 else 0
    }

    fun updateList(list: MutableList<Email>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun removeItem(position: Int) {
        notifyItemRemoved(position)
    }

    override fun putBackItem(position: Int) {
        notifyItemInserted(position)
    }
}
