package com.example.mng.vertexdelivery.callback

import com.example.mng.vertexdelivery.model.UserModel

interface IUserLoadCallback {
    fun onUserLoadSuccess(userModelList:List<UserModel>)
    fun onUserLoadFaild(message:String)
}