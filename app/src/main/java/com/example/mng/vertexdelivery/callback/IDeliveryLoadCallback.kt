package com.example.mng.vertexdelivery.callback

import com.example.mng.vertexdelivery.model.DeliveryModel

interface IDeliveryLoadCallback {
    fun onDeliveryLoadSuccess(deliveryModelList:List<DeliveryModel>)
    fun onDeliveryLoadFaild(message:String)
}