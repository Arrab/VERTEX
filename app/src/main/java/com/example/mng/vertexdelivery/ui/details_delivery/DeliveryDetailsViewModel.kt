package com.example.mng.vertexdelivery.ui.details_delivery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.model.DeliveryModel

class DeliveryDetailsViewModel : ViewModel() {

    private var mutableLiveData: MutableLiveData<DeliveryModel>? = null
    private var mutableStatusLiveData: MutableLiveData<DeliveryModel>? = null

    init {
        mutableStatusLiveData = MutableLiveData()
    }

    fun getMutableLiveData(): MutableLiveData<DeliveryModel> {
        if (mutableLiveData == null)
            mutableLiveData = MutableLiveData()
        mutableLiveData!!.value = Common.deliverySelected
        return mutableLiveData!!
    }

    fun getMutableStatusLiveData(): MutableLiveData<DeliveryModel> {
        if (mutableStatusLiveData == null)
            mutableStatusLiveData = MutableLiveData()
        mutableStatusLiveData!!.value = Common.deliverySelected
        return mutableStatusLiveData!!
    }

    fun setStatusModel(statusModel: DeliveryModel) {
        if (mutableStatusLiveData != null)
            mutableStatusLiveData!!.value = (statusModel)
    }

}