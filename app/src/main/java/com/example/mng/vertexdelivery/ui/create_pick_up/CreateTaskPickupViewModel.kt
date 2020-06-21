package com.example.mng.vertexdelivery.ui.create_pick_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mng.vertexdelivery.model.PickUpModel

class CreateTaskPickupViewModel: ViewModel() {
    private var mutableLiveData: MutableLiveData<PickUpModel>? = null
    private var mutableStatusLiveData: MutableLiveData<PickUpModel>? = null

    init {
        mutableStatusLiveData = MutableLiveData()
    }
}