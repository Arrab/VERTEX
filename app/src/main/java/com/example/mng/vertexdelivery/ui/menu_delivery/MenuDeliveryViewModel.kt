package com.example.mng.vertexdelivery.ui.menu_delivery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mng.vertexdelivery.callback.IMenuDeliveryCallbackListener
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.model.DeliveryModel
import com.example.mng.vertexdelivery.model.PickUpModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuDeliveryViewModel : ViewModel(), IMenuDeliveryCallbackListener {

    private var categoriesListMutable: MutableLiveData<List<DeliveryModel>>? = null
    private var messageError: MutableLiveData<String> = MutableLiveData()
    private var menuDeliveryCallbackListener: IMenuDeliveryCallbackListener
    private var mutableStatusLiveData:MutableLiveData<DeliveryModel>? = null

    init {
        menuDeliveryCallbackListener = this
        mutableStatusLiveData = MutableLiveData()
    }

    override fun onMenuDeliveryLoadSuccess(deliveryMenuModel: List<DeliveryModel>) {
        categoriesListMutable?.value = deliveryMenuModel
    }

    override fun onMenuDeliveryLoadFaild(message: String) {
        messageError.value = message
    }

    fun setCategoryList(statusModel: DeliveryModel){
        val list = mutableListOf<DeliveryModel>()
        list.addAll(categoriesListMutable!!.value!!)
        if (categoriesListMutable != null)
            list.add(statusModel!!)
        categoriesListMutable!!.value = list
    }

    fun getCategoryList(): MutableLiveData<List<DeliveryModel>> {
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
        val tempListDeliv = ArrayList<DeliveryModel>()
        val delivMenuRef = FirebaseDatabase.getInstance().getReference(Common.DELIVERY_REF)
        delivMenuRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                menuDeliveryCallbackListener.onMenuDeliveryLoadFaild(p0.message!!)
            }

            override fun onDataChange(p0: DataSnapshot) {
                var position: Int = 0
                for (itemSanpshot in p0!!.children) {
                    val model = itemSanpshot.getValue<DeliveryModel>(DeliveryModel::class.java)
                    if (model!!.package_id != "0")
                        tempListDeliv.add(model!!)
                }
                menuDeliveryCallbackListener.onMenuDeliveryLoadSuccess(tempListDeliv)
            }

        })
    }

    fun setCreateModel(statusModel: DeliveryModel) {
        if (mutableStatusLiveData != null)
            mutableStatusLiveData!!.value = (statusModel)
    }

    fun getMutableCreateLiveData(): MutableLiveData<DeliveryModel> {
        if (mutableStatusLiveData == null)
            mutableStatusLiveData = MutableLiveData()
        mutableStatusLiveData!!.value = Common.deliverySelected
        return mutableStatusLiveData!!
    }
}