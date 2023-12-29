package com.placefy.app.activities.ui.admin.properties

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PropertyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is properties Fragment"
    }
    val text: LiveData<String> = _text
}