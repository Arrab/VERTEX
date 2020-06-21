package com.example.mng.vertexdelivery.ui.details_pick_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.model.PickUpModel

class PickUpDetailsViewModel : ViewModel() {

    private var mutableLiveData: MutableLiveData<PickUpModel>? = null
    private var mutableStatusLiveData: MutableLiveData<PickUpModel>? = null

    init {
        mutableStatusLiveData = MutableLiveData()
    }

    fun getMutableLiveData(): MutableLiveData<PickUpModel> {
        if (mutableLiveData == null)
            mutableLiveData = MutableLiveData()
        mutableLiveData!!.value = Common.pickupSelected
        return mutableLiveData!!
    }

    fun getMutableStatusLiveData(): MutableLiveData<PickUpModel> {
        if (mutableStatusLiveData == null)
            mutableStatusLiveData = MutableLiveData()
        mutableStatusLiveData!!.value = Common.pickupSelected
        return mutableStatusLiveData!!
    }

    fun setStatusModel(statusModel: PickUpModel) {
        if (mutableStatusLiveData != null)
            mutableStatusLiveData!!.value = (statusModel)
    }

}