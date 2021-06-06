package com.cafe.noteapp.ui.base

import androidx.lifecycle.*
import com.cafe.noteapp.util.livedata.ActivityActionLiveData
import com.cafe.noteapp.util.livedata.FragmentActionLiveData

/**
 * All of ViewModels should be inherited from [BaseViewModel]
 */
abstract class BaseViewModel() :
    ViewModel(), LifecycleObserver {

    val activityAction = ActivityActionLiveData()
    val fragmentAction = FragmentActionLiveData()

    /**
     * We can use lifeCycle in inherited classes if we need
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    /**
     * We can use lifeCycle in inherited classes if we need
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }
}
