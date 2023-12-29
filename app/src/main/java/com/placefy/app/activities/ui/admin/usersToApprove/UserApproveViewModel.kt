package com.placefy.app.activities.ui.admin.usersToApprove

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserApproveViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Approve Fragment"
    }
    val text: LiveData<String> = _text
}