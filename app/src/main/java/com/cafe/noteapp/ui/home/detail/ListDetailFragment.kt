package com.cafe.noteapp.ui.home.detail

import android.util.Log
import android.view.View
import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.FragmentListDetailBinding
import com.cafe.noteapp.databinding.ListRowItemBinding
import com.cafe.noteapp.ui.base.BaseFragment
import com.cafe.noteapp.ui.base.ViewModelScope
import com.cafe.noteapp.ui.home.list.HomeListFragmentDirections
import com.cafe.noteapp.ui.home.list.HomeListViewModel
import com.cafe.noteapp.ui.home.list.ListItem
import com.cafe.noteapp.ui.home.list.adapter.MultiLayoutAdapter
import com.cafe.noteapp.util.extentions.findNaveController
import com.cafe.noteapp.util.extentions.observeSafe

class ListDetailFragment : BaseFragment<ListDetailViewModel, FragmentListDetailBinding>(),
        View.OnClickListener {
    override val viewModel: ListDetailViewModel by getLazyViewModel(ViewModelScope.FRAGMENT)
    override val layoutId: Int = R.layout.fragment_list_detail

    override fun onViewInitialized(binding: FragmentListDetailBinding) {
        super.onViewInitialized(binding)
        initClicks()
        viewModel.currentFolderId = requireArguments().getInt("folderId", -1)
        binding.listDetailTitle.text = requireArguments().getString("folderName", "نام پوشه")
        viewModel.getAllNotes(
                viewModel.currentFolderId.toString()
        )

        binding.adapter = MultiLayoutAdapter<ListItem, ListRowItemBinding>(
                layoutId = R.layout.list_row_item,
                onItemClicked = {
                    findNaveController()
                            .navigate(ListDetailFragmentDirections.actionListDetailFragmentToNoteDetailFragment(
                                    viewModel.currentFolderId,
                                    it.id
                            ))

                }
        )

        viewModel.allNotesLiveData.observeSafe(viewLifecycleOwner) {
            binding.adapter?.swapItems(it)
            Log.d(TAG, "onViewInitialized: $it")
        }

    }

    private fun initClicks() {
        binding.listDetailBackBtn.setOnClickListener(this)
        binding.addNewNote.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.listDetailBackBtn -> {
                findNaveController().popBackStack()
            }

            R.id.addNewNote -> {
                findNaveController().navigate(
                        ListDetailFragmentDirections.actionListDetailFragmentToNoteDetailFragment(
                                requireArguments().getInt("folderId", -1),
                                0
                        )
                )
            }

        }
    }

    companion object {
        const val TAG = "ListDetailFragment"
    }
}