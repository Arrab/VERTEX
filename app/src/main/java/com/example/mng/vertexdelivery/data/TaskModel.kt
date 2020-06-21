package com.example.mng.vertexdelivery.data

import android.util.Log
import com.google.firebase.database.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

object TaskModel: Observable() {

    private var mValueDataListener: ValueEventListener? = null
    private var mTaskList: ArrayList<Task>? = ArrayList()

    private fun getDataBaseRef(): DatabaseReference? {
        return FirebaseDatabase.getInstance().reference.child("Task")
    }

    init {
        if(mValueDataListener != null){
            getDataBaseRef()?.removeEventListener(mValueDataListener!!)
        }
        mValueDataListener = null
        Log.i("TaskModel","Data init line 24")

        mValueDataListener = object: ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    Log.i("TaskModel","Data Update line 29")
                    val data:ArrayList<Task> = ArrayList()
                    if (dataSnapshot != null){
                        for (snapshot: DataSnapshot in dataSnapshot
                            .children){
                            try {
                                if (Task(snapshot).status == "false"){
                                    data.add(Task(snapshot))
                                }
                            } catch (e: Exception){
                                e.printStackTrace()
                            }
                        }
                        mTaskList = data
                        Log.i("TaskModel","data update, there are "+ mTaskList!!.size+" entrees in the catch")
                        setChanged()
                        notifyObservers()
                    }
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                if(p0 != null){
                    Log.i("TaskModel", "line 51 data update canceled, err = ${p0.message}")
                }
            }
        }
        getDataBaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }

    fun getData(): ArrayList<Task>?{
        return mTaskList
    }

}