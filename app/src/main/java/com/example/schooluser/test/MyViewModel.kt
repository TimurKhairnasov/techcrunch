package com.example.schooluser.test

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    var data = MutableLiveData<List<NewItem>>()
    var data2 = MutableLiveData<List<NewItemRus>>()
}