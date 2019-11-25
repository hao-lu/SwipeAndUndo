//package com.haolu.swipeandundo
//
//import androidx.core.view.ViewCompat
//import androidx.recyclerview.widget.RecyclerView
//import jp.wasabeef.recyclerview.animators.BaseItemAnimator
//
//class PushUpAnimator : BaseItemAnimator() {
//
//    override fun animateRemoveImpl(holder: RecyclerView.ViewHolder?) {
//        ViewCompat.animate(holder?.itemView!!)
//            .translationY((-holder.itemView.height / 2).toFloat())
//            .scaleY(0f)
//            .setDuration(removeDuration)
//            .setInterpolator(mInterpolator)
//            .setListener(DefaultRemoveVpaListener(holder))
//            .setStartDelay(getRemoveDelay(holder))
//            .start()
//    }
//
//    override fun animateAddImpl(holder: RecyclerView.ViewHolder?) {
//        ViewCompat.animate(holder?.itemView!!)
//            .alpha(1f)
//            .setDuration(addDuration)
//            .setInterpolator(mInterpolator)
//            .setListener(DefaultAddVpaListener(holder))
//            .setStartDelay(getAddDelay(holder))
//            .start()
//    }
//
//    override fun preAnimateAddImpl(holder: RecyclerView.ViewHolder?) {
//        ViewCompat.setAlpha(holder?.itemView, 0f)
//    }
//}
//
//
