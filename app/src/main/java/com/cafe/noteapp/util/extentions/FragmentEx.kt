package com.cafe.noteapp.util.extentions

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

fun Fragment.findNaveController() =
    NavHostFragment.findNavController(this)
