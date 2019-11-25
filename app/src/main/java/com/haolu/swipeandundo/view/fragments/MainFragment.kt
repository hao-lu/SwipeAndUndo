package com.haolu.swipeandundo.view.fragments

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.haolu.swipeandundo.R
import com.haolu.swipeandundo.config
import com.haolu.swipeandundo.databinding.FragmentMainBinding
import com.haolu.swipeandundo.view.adapters.AnimationOnLastItemAdapter
import com.haolu.swipeandundo.view.adapters.NoAnimationOnLastItemAdapter
import com.haolu.swipeandundo.view.helpers.SwipeRemoveActionCallback
import com.haolu.swipeandundo.view.helpers.SwipeRemoveItemDecoration
import com.haolu.swipeandundo.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var swipeItemTouchHelper: ItemTouchHelper
    private lateinit var background: ColorDrawable

    private var icon: Drawable? = null

    private val adapter = AnimationOnLastItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        background =
            ColorDrawable(ContextCompat.getColor(requireContext(), R.color.colorAccent))
        icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_white_24dp)

        binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )
            .apply {
                recyclerview.adapter = adapter
                recyclerview.layoutManager = LinearLayoutManager(context)
                recyclerview.addItemDecoration(
                    SwipeRemoveItemDecoration(
                        background,
                        icon
                    )
                )
            }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.removedItemIndex.observe(this, Observer {
            showUndoSnackbar(it)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.list.observe(this, Observer {
            adapter.updateList(it)
        })

        swipeItemTouchHelper = ItemTouchHelper(
            SwipeRemoveActionCallback(
                background,
                icon,
                arrayListOf(viewModel, adapter),
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            )
        )
        swipeItemTouchHelper.attachToRecyclerView(binding.recyclerview)
    }

    private fun showUndoSnackbar(position: Int) {
        val snackbar = Snackbar.make(
            binding.recyclerview,
            getString(R.string.moved_to_trash),
            Snackbar.LENGTH_LONG
        ).setAction("UNDO") {
            viewModel.putBackItem(position)
            adapter.putBackItem(position)
        }
        snackbar.config(requireContext())
        snackbar.show()
    }
}
