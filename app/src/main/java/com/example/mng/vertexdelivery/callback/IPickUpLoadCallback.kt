package com.example.mng.vertexdelivery.callback

import com.example.mng.vertexdelivery.model.PickUpModel

interface IPickUpLoadCallback {
    fun onPickUpLoadSuccess(pickUpModelList:List<PickUpModel>)
    fun onPickUpLoadFaild(message:String)
}