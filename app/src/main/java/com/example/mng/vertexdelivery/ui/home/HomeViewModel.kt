package com.example.mng.vertexdelivery.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mng.vertexdelivery.callback.IDeliveryLoadCallback
import com.example.mng.vertexdelivery.callback.IPickUpLoadCallback
import com.example.mng.vertexdelivery.callback.IUserLoadCallback
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.model.DeliveryModel
import com.example.mng.vertexdelivery.model.PickUpModel
import com.example.mng.vertexdelivery.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel : ViewModel(), IPickUpLoadCallback, IDeliveryLoadCallback, IUserLoadCallback {

    override fun onDeliveryLoadSuccess(deliveryModelList: List<DeliveryModel>) {
        deliveryListMutableLiveData!!.value = deliveryModelList
    }

    override fun onDeliveryLoadFaild(message: String) {
        messageError.value = message
    }

    override fun onPickUpLoadSuccess(pickUpModelList: List<PickUpModel>) {
        pickUpListMutableLiveData!!.value = pickUpModelList
    }

    override fun onPickUpLoadFaild(message: String) {
        messageError.value = message
    }

    override fun onUserLoadSuccess(userModelList: List<UserModel>) {
        userListMutableLiveData!!.value =userModelList
    }

    override fun onUserLoadFaild(message: String) {
        messageError.value = message
    }

    private var deliveryListMutableLiveData: MutableLiveData<List<DeliveryModel>>? = null
    private var pickUpListMutableLiveData: MutableLiveData<List<PickUpModel>>? = null
    private var userListMutableLiveData: MutableLiveData<List<UserModel>>? = null
    private lateinit var messageError: MutableLiveData<String>
    private var pickUpLoadCallbackListener: IPickUpLoadCallback
    private var deliveryLoadCallbackListener: IDeliveryLoadCallback
    private var userLoadCallbaclListener: IUserLoadCallback

    //PickUp
    fun setHomePickUpList(statusModel: List<PickUpModel>){
        if (pickUpListMutableLiveData != null)
            pickUpListMutableLiveData!!.value = statusModel
    }
    fun getHomePickUpList(): MutableLiveData<List<PickUpModel>> {
        if (pickUpListMutableLiveData == null) {
            pickUpListMutableLiveData = MutableLiveData()
            loadPickUpList()
        }
        return pickUpListMutableLiveData!!
    }
    fun setUserActual(userModel: List<UserModel>){
        if (userListMutableLiveData != null)
            userListMutableLiveData!!.value = userModel
    }

    fun getUserActual(): MutableLiveData<List<UserModel>>{
        if (userListMutableLiveData == null){
            userListMutableLiveData = MutableLiveData()
            loadUserList()
        }
        return userListMutableLiveData!!
    }

    private fun loadUserList(){
        val tempListUser = ArrayList<UserModel>()
        val userRef = FirebaseDatabase.getInstance().getReference(Common.USER_REF)

        userRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                userLoadCallbaclListener.onUserLoadFaild(error.message!!)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                tempListUser.clear()
                for (itemSnapshot in snapshot!!.children) {
                    val model = itemSnapshot.getValue<UserModel>(UserModel::class.java)
                    if(model!!.user_id == Common.currentUser_id) {
                        tempListUser.add(model!!)
                    }
                }
                Common.currentUser = tempListUser.get(0)
                userLoadCallbaclListener.onUserLoadSuccess(tempListUser)
            }
        })
    }
    private fun loadPickUpList() {
        val tempList = ArrayList<PickUpModel>()
        val pickUpRef = FirebaseDatabase.getInstance().getReference(Common.PICKUP_REF)

        pickUpRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                pickUpLoadCallbackListener.onPickUpLoadFaild(error.message!!)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                    tempList.clear()
                    for (itemSnapshot in snapshot!!.children) {
                        val model = itemSnapshot.getValue<PickUpModel>(PickUpModel::class.java)
                        if(model!!.task_id != "task_0") {
                            tempList.add(model!!)
                        }
                    }
                    Common.pickupListSelected = tempList
                    pickUpLoadCallbackListener.onPickUpLoadSuccess(tempList)
            }
        })
    }

    //Delivery
    fun setHomeDeliveryList(statusModel: List<DeliveryModel>){

        if (deliveryListMutableLiveData != null)
            deliveryListMutableLiveData!!.value = statusModel
    }
    fun getHomeDeliveryList(): MutableLiveData<List<DeliveryModel>> {
        if (deliveryListMutableLiveData == null) {
            deliveryListMutableLiveData = MutableLiveData()
            loadDeliveryList()
        }
        return deliveryListMutableLiveData!!
    }


    private fun loadDeliveryList() {
        val tempListDelv = ArrayList<DeliveryModel>()
        val deliveryRef = FirebaseDatabase.getInstance().getReference(Common.DELIVERY_REF)

        deliveryRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                deliveryLoadCallbackListener.onDeliveryLoadFaild(p0.message!!)
            }

            override fun onDataChange(p0: DataSnapshot) {
                tempListDelv.clear()
                for (itemSanpshotDelv in p0!!.children) {
                    val modelDelv = itemSanpshotDelv.getValue<DeliveryModel>(DeliveryModel::class.java)
                    if (modelDelv!!.package_id != "package_0")
                    tempListDelv.add(modelDelv!!)
                }
                Common.deliveryListSelected = tempListDelv
                deliveryLoadCallbackListener.onDeliveryLoadSuccess(tempListDelv)
            }

        })
    }


    init {

        deliveryLoadCallbackListener = this
        pickUpLoadCallbackListener = this
        userLoadCallbaclListener = this
    }


}