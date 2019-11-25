package com.haolu.swipeandundo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haolu.swipeandundo.model.Email
import com.haolu.swipeandundo.view.helpers.SwipeRemoveActionListener
import com.haolu.swipeandundo.view.helpers.UndoActionListener

class MainViewModel : ViewModel(), SwipeRemoveActionListener,
    UndoActionListener {

    private val _list: MutableLiveData<MutableList<Email>> = MutableLiveData()
    val list: LiveData<MutableList<Email>> = _list

    private val _deletedPosition: MutableLiveData<Int> = MutableLiveData()
    val removedItemIndex: LiveData<Int> = _deletedPosition

    private var previousDeletedItem: Email? = null

    init {
        fetchEmails()
    }

    fun fetchEmails() {
        val subject = "Lorem ipsum"
        val body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquam " +
                "mauris sit amet nulla elementum, vitae pulvinar augue luctus. Ut leo tortor, " +
                "suscipit sit amet aliquet vitae, suscipit nec dui. Ut placerat, libero ut " +
                "sagittis sagittis, nulla velit consequat dui, condimentum luctus arcu leo ac quam."
                "Aenean cursus est purus, nec scelerisque sapien accumsan id. Integer aliquet" +
                " a nibh at faucibus. Nulla et neque egestas, venenatis quam venenatis, maximus" +
                " arcu. Ut eget dignissim nulla. Integer at felis turpis. Mauris quis est ante."

        val list = arrayListOf<Email>()
        list.add(Email("Khada Jhin", subject, body, "Oct 11"))
        list.add(Email("Sarah Fortune", subject, body, "Oct 11"))
        list.add(Email("Shauna Vayne", subject, body, "Oct 11"))
        list.add(Email("Xayah Lhotlan", subject, body, "Oct 11"))
        list.add(Email("Ashe Avarosa", subject, body, "Oct 11"))

        _list.value = list
    }

    override fun removeItem(position: Int) {
        previousDeletedItem = _list.value?.get(position)
        _list.value?.removeAt(position)
        _deletedPosition.value = position
    }

    override fun putBackItem(position: Int) {
        _list.value?.add(position, previousDeletedItem!!)
    }
}
