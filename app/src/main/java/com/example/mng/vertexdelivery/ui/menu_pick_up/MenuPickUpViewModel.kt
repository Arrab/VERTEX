package com.example.mng.vertexdelivery.ui.menu_pick_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mng.vertexdelivery.callback.IMenuPickUpCallbackListener
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.model.PickUpModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuPickUpViewModel : ViewModel(), IMenuPickUpCallbackListener {

    private var categoriesListMutable: MutableLiveData<List<PickUpModel>>? = null
    private var messageError: MutableLiveData<String> = MutableLiveData()
    private var menuPickUpCallbackListener: IMenuPickUpCallbackListener
    private var mutableStatusLiveData:MutableLiveData<PickUpModel>? = null

    init {
        menuPickUpCallbackListener = this
        mutableStatusLiveData = MutableLiveData()
    }

    override fun onMenuPickUpLoadSuccess(pickUpMenuModel: List<PickUpModel>) {
        categoriesListMutable?.value = pickUpMenuModel
    }

    override fun onMenuPickUpLoadFaild(message: String) {
        messageError.value = message
    }

    fun getCategoryList(): MutableLiveData<List<PickUpModel>> {
        if (categoriesListMutable == null) {
            categoriesListMutable = MutableLiveData()
            loadCategory()
        }
        return categoriesListMutable!!
    }

    fun getMessageError(): MutableLiveData<String> {
        return messageError
    }

    private fun loadCategory() {
        val tempListPickUp = ArrayList<PickUpModel>()
        val pickUpMenuRef = FirebaseDatabase.getInstance().getReference(Common.PICKUP_REF)
        pickUpMenuRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                menuPickUpCallbackListener.onMenuPickUpLoadFaild(p0.message!!)
            }

            override fun onDataChange(p0: DataSnapshot) {
                var position: Int = 0
                for (itemSanpshot in p0!!.children) {
                    val model = itemSanpshot.getValue<PickUpModel>(PickUpModel::class.java)
                    if (model!!.task_id != "task_0")
                        tempListPickUp.add(model!!)
                }
                menuPickUpCallbackListener.onMenuPickUpLoadSuccess(tempListPickUp)
            }

        })
    }

    fun setCreateModel(statusModel: PickUpModel) {
        if (mutableStatusLiveData != null)
            mutableStatusLiveData!!.value = (statusModel)
    }

    fun getMutableCreateLiveData(): MutableLiveData<PickUpModel> {
        if (mutableStatusLiveData == null)
            mutableStatusLiveData = MutableLiveData()
        mutableStatusLiveData!!.value = Common.pickupSelected
        return mutableStatusLiveData!!
    }
}