package com.placefy.app.activities.ui.admin.plans

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlansViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is plans Fragment"
    }
    val text: LiveData<String> = _text
}