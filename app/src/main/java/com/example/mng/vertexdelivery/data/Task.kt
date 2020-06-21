package com.example.mng.vertexdelivery.data

import com.google.firebase.database.DataSnapshot
import java.io.Serializable


class Task (snapshot: DataSnapshot): Serializable{
    lateinit var id:String
    lateinit var name: String
    lateinit var phone:String
    lateinit var address:String
    lateinit var time:String
    lateinit var status:String

    init {
        try {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key.toString()
            name = data["name"].toString()
            address = data["address"].toString()
            phone = data["phone"].toString()
            time = data["time"].toString()
            status = data["status"].toString()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}