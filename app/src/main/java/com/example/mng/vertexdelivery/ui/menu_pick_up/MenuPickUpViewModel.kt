package com.example.mng.vertexdelivery.ui.menu_pick_up

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mng.vertexdelivery.callback.IMenuPickUpCallbackListener
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.model.PickUpModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    fun setCategoryList(statusModel: PickUpModel){
        val list = mutableListOf<PickUpModel>()
        list.addAll(categoriesListMutable!!.value!!)
        if (categoriesListMutable != null)
            list.add(statusModel!!)
        categoriesListMutable!!.value = list
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

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(p0: DataSnapshot) {
                tempListPickUp.clear()
                var position: Int = 0
                for (itemSanpshot in p0!!.children) {
                    val model = itemSanpshot.getValue<PickUpModel>(PickUpModel::class.java)
                    if (model!!.task_id != "0") {
                        val dateSt = model!!.date
                        val df = DateTimeFormatter.ofPattern("yyyy/MM/dd")
                        val date = LocalDate.parse(dateSt, df)
                        val day = date.dayOfYear
                        val currentDate =
                            LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ISO_DATE)
                        val dayNow = currentDate.dayOfYear
                        if (model!!.status == Common.STATUS_DONE) {
                            if (day == dayNow)
                                tempListPickUp.add(model!!)
                        } else {
                            tempListPickUp.add(model!!)
                        }
                    }
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