package com.app.wetravel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val currentUser: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }
}