package com.cafe.noteapp.ui.home.note

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.FragmentNoteDetailBinding
import com.cafe.noteapp.ui.base.BaseFragment
import com.cafe.noteapp.ui.base.ViewModelScope
import com.cafe.noteapp.ui.home.list.NoteItem
import com.cafe.noteapp.util.hepers.DateHelper
import com.cafe.noteapp.util.extentions.findNaveController
import com.cafe.noteapp.util.extentions.observeSafe

class NoteDetailFragment : BaseFragment<NoteDetailViewModel, FragmentNoteDetailBinding>(),
    View.OnClickListener {
    val TAG = this::class.java.simpleName
    override val viewModel: NoteDetailViewModel by getLazyViewModel(ViewModelScope.FRAGMENT)
    override val layoutId: Int = R.layout.fragment_note_detail

    override fun onViewInitialized(binding: FragmentNoteDetailBinding) {
        super.onViewInitialized(binding)
        initClicks()
        initObservers()
        viewModel.currentFolderId = requireArguments().getInt("folderId")
        viewModel.currentNoteId = (requireArguments().getInt("noteId", 0))


        viewModel.savedNoteItem.observeSafe(viewLifecycleOwner) {
            binding.note = it
        }
    }

    private fun initClicks() {
        binding.listDetailBackBtn.setOnClickListener(this)
    }

    private fun initObservers() {
        viewModel.loadingVisibilityLiveData.observeSafe(viewLifecycleOwner) {
            binding.noteLading.isVisible = it
        }

        (requireArguments().getInt("noteId", 0)).takeIf { it != 0 }?.let {
            viewModel.getNoteById(it)
        }

        viewModel.isInsertionOrUpdateDone.observeSafe(viewLifecycleOwner) { isDone ->
            if (isDone)
                findNaveController().popBackStack()
        }


    }


    //todo clear up this mess later too
    private fun saveNote() {
        val title = binding.noteDetailTitle.text.toString()
        if (title.isEmpty()) {
            Toast.makeText(context, "لطفا عنوان یادداشت خود را وارد کنید", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val content = binding.noteDetailContent.text.toString()

        viewModel.saveNote(
            NoteItem(
                id = 0,
                folderId = requireArguments().getInt("folderId", 0),
                content = content,
                title = title,
                created_data = DateHelper.getCurrentDateInMilli()
            )
        )
    }

    private fun updateNote() {
        val title = binding.noteDetailTitle.text.toString()
        if (title.isEmpty()) {
            Toast.makeText(context, "لطفا عنوان یادداشت خود را وارد کنید", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val content = binding.noteDetailContent.text.toString()

        viewModel.updateNote(
            NoteItem(
                id = viewModel.currentNoteId,
                folderId = requireArguments().getInt("folderId", 0),
                content = content,
                title = title,
                created_data = DateHelper.getCurrentDateInMilli()
            )
        )
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.listDetailBackBtn -> {
                if (viewModel.currentNoteId == 0)
                    saveNote()
                else
                    updateNote()
            }
        }
    }

}