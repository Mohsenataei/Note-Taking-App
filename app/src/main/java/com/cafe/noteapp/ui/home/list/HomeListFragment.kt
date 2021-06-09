package com.cafe.noteapp.ui.home.list

import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.core.view.isVisible
import com.cafe.noteapp.R
import com.cafe.noteapp.bus.EventBus
import com.cafe.noteapp.databinding.FragmentHomeListBinding
import com.cafe.noteapp.databinding.ListRowItemBinding
import com.cafe.noteapp.ui.base.BaseFragment
import com.cafe.noteapp.ui.base.ViewModelScope
import com.cafe.noteapp.ui.home.dialog.NewFolderDialog
import com.cafe.noteapp.ui.home.list.HomeListViewModel.Companion.FOLDER
import com.cafe.noteapp.ui.home.list.HomeListViewModel.Companion.NOTE
import com.cafe.noteapp.ui.home.list.HomeListViewModel.Companion.TAG
import com.cafe.noteapp.ui.home.list.adapter.MultiLayoutAdapter
import com.cafe.noteapp.util.extentions.DeleteDialog
import com.cafe.noteapp.util.extentions.findNaveController
import com.cafe.noteapp.util.extentions.observeSafe
import javax.inject.Inject


class HomeListFragment : BaseFragment<HomeListViewModel, FragmentHomeListBinding>(),
    View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    override val layoutId: Int
        get() = R.layout.fragment_home_list

    override val viewModel: HomeListViewModel by getLazyViewModel(ViewModelScope.ACTIVITY)

    @Inject
    lateinit var eventBus: EventBus

    override fun onViewInitialized(binding: FragmentHomeListBinding) {
        super.onViewInitialized(binding)
        initView()
        binding.viewModel = viewModel
        binding.adapter = MultiLayoutAdapter<ListItem, ListRowItemBinding>(
            layoutId = R.layout.list_row_item,
            onItemClicked = {
                Log.d(TAG, "onViewInitialized: $it")
                when (it.type) {
                    FOLDER -> findNaveController().navigate(
                        HomeListFragmentDirections
                            .actionHomeListFragmentToListDetailFragment(
                                it.id,
                                it.name
                            )
                    )

                    NOTE -> findNaveController().navigate(
                        HomeListFragmentDirections.actionHomeListFragmentToNoteDetailFragment(
                            0,
                            it.id
                        )
                    )

                }

            },
            onBind = {
                listViewModel = viewModel
            }
        )

        viewModel.listItemLiveData.observeSafe(viewLifecycleOwner) {
            binding.adapter?.swapItems(it)
        }

        viewModel.loadingVisibility.observeSafe(viewLifecycleOwner) {
            binding.loading.isVisible = it
        }

        viewModel.deleteEventLiveData.observeSafe(viewLifecycleOwner) {
            DeleteDialog(
                getString(R.string.delete_note),
                getString(R.string.are_you_sure_want_to_delete_this_folder),
                onConfirm = {
                    viewModel.deleteNote(it.noteId)
                },
                onCancel = {
                    Toast.makeText(
                        requireContext(),
                        "shiit you can cancel deleting note by id ${it.noteId}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

    }

    private fun createOptionMenu(view: View) {
        val popUpMenu = PopupMenu(requireContext(), view)
        popUpMenu.setOnMenuItemClickListener(this)
        popUpMenu.inflate(R.menu.more_options_menu)
        popUpMenu.show()
    }

    private fun initView() {
        binding.plusBtn.setOnClickListener(this)
        binding.addNoteBtn.setOnClickListener(this)
        binding.addFolderBtn.setOnClickListener(this)
    }

    private fun addFolderDialog() {
        val newFolderDialog = NewFolderDialog(
            onConfirm = {
                viewModel.insertNewFolder(it)
            },
            onCancel = {

            }
        )

        newFolderDialog.show(childFragmentManager, newFolderDialog.tag)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.plusBtn -> {
                binding.addFolderBtn.isVisible = true
                binding.addNoteBtn.isVisible = true
                createOptionMenu(view)
            }

            R.id.addFolderBtn -> {
                addFolderDialog()
            }

            R.id.addNoteBtn -> {
                findNaveController().navigate(
                    HomeListFragmentDirections.actionHomeListFragmentToNoteDetailFragment(
                        0,
                        0
                    )
                )
            }
        }
    }

    override fun onMenuItemClick(menu: MenuItem?): Boolean {
        when (menu?.itemId) {
            R.id.option_delete -> {
                Toast.makeText(
                    context,
                    "Are you sure want to delete this item ?",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }
            R.id.option_edit -> {
                Toast.makeText(context, "Are you sure want to edit this item ?", Toast.LENGTH_SHORT)
                    .show()
                return true
            }
        }

        return false
    }
}