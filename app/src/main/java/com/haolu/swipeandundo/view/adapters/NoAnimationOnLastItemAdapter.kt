package com.haolu.swipeandundo.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.haolu.swipeandundo.BR
import com.haolu.swipeandundo.R
import com.haolu.swipeandundo.model.Email
import com.haolu.swipeandundo.view.helpers.SwipeRemoveActionListener
import com.haolu.swipeandundo.view.helpers.UndoActionListener

class NoAnimationOnLastItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    SwipeRemoveActionListener, UndoActionListener {

    private var list: List<Email>? = null

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ViewDataBinding? = DataBindingUtil.bind(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.binding?.setVariable(BR.email, list!![position])
            holder.binding?.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_email_content, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0
    }

    fun updateList(list: List<Email>) {
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
