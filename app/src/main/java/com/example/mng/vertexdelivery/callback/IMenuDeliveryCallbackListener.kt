package com.example.mng.vertexdelivery.callback

import com.example.mng.vertexdelivery.model.DeliveryModel

interface IMenuDeliveryCallbackListener {
    fun onMenuDeliveryLoadSuccess(deliveryMenuModel:List<DeliveryModel>)
    fun onMenuDeliveryLoadFaild(message:String)
}