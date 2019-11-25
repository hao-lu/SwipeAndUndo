package com.haolu.swipeandundo.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.haolu.swipeandundo.BR
import com.haolu.swipeandundo.R
import com.haolu.swipeandundo.model.Email

class AnimationOnLastItemAdapter : SwipeRemoveItemAdapter() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ViewDataBinding? = DataBindingUtil.bind(view)
    }

    private var list: MutableList<Email>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_FOOTER -> createFooterViewHolder(parent.context)
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_email_content, parent, false)
                return ItemViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.binding?.setVariable(BR.email, list!![position])
            holder.binding?.executePendingBindings()
        }
    }

    fun updateList(list: MutableList<Email>) {
        this.list = list
        listSize = list.size
        notifyDataSetChanged()
    }
}
