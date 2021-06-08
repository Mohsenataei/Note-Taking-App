package com.cafe.noteapp.ui.home.note

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.FragmentNoteDetailBinding
import com.cafe.noteapp.ui.base.BaseFragment
import com.cafe.noteapp.ui.base.ViewModelScope
import com.cafe.noteapp.ui.home.list.NoteItem
import com.cafe.noteapp.util.DateHelper
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

        viewModel.savedNoteItem.observeSafe(viewLifecycleOwner) {
            binding.note = it
        }
    }

    private fun initClicks() {
        binding.listDetailBackBtn.setOnClickListener(this)
    }

    private fun initObservers() {
        viewModel.noteLoadingLiveData.observeSafe(viewLifecycleOwner) {
            binding.noteLading.isVisible = it
        }

        (requireArguments().getInt("noteId", 0)).takeIf { it != 0 }?.let {
            viewModel.getNoteById(it)
        }

        viewModel.isInsertionDone.observeSafe(viewLifecycleOwner) { isDone ->
            if (isDone)
                findNaveController().popBackStack()
        }


    }

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
                        0, requireArguments().getInt("folderId", 0), content, title, DateHelper.getCurrentDateInMilli()
                )
        )
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.listDetailBackBtn -> {
                saveNote()
            }
        }
    }

}