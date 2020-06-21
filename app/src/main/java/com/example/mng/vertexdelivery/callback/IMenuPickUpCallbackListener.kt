package com.example.mng.vertexdelivery.callback

import com.example.mng.vertexdelivery.model.PickUpModel

interface IMenuPickUpCallbackListener {
    fun onMenuPickUpLoadSuccess(pickUpMenuModel:List<PickUpModel>)
    fun onMenuPickUpLoadFaild(message:String)
}