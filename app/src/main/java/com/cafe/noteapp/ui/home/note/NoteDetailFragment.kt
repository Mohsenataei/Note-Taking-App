package com.cafe.noteapp.ui.home.note

import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.FragmentNoteDetailBinding
import com.cafe.noteapp.ui.base.BaseFragment
import com.cafe.noteapp.ui.base.ViewModelScope

class NoteDetailFragment : BaseFragment<NoteDetailViewModel, FragmentNoteDetailBinding>() {
    override val viewModel: NoteDetailViewModel by getLazyViewModel(ViewModelScope.FRAGMENT)
    override val layoutId: Int = R.layout.fragment_note_detail

}