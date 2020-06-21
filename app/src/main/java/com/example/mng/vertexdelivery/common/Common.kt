package com.example.mng.vertexdelivery.common

import com.example.mng.vertexdelivery.model.PickUpModel
import com.example.mng.vertexdelivery.model.UserModel
import com.google.firebase.database.FirebaseDatabase

object Common {
    var pickupSelected: PickUpModel? = null
    val STATUS_DONE: String = "done"
    val STATUS_INPROGRESS: String = "inprogress"
    val STATUS_FREE: String = "free"
    val txt_DONE: String = "Done"
    val txt_INPROGRESS: String = "In Progress"
    val txt_FREE: String = "Free"
    val IMG_PICKUP_DONE: String = "https://firebasestorage.googleapis.com/v0/b/vertexdelivery-5c364.appspot.com/o/icons%2Fpickup_done.png?alt=media&token=06a8e230-50db-470e-b74d-1914cce16def"
    val IMG_FREE: String = "https://firebasestorage.googleapis.com/v0/b/vertexdelivery-5c364.appspot.com/o/icons%2Fpickup_free.png?alt=media&token=bb7fab68-22e1-446c-a134-c5f5b534c60c"
    val IMG_PICKUP_INPROGRESS: String = "https://firebasestorage.googleapis.com/v0/b/vertexdelivery-5c364.appspot.com/o/icons%2Fpickup_progress.png?alt=media&token=c3f1bd90-7bf8-40d6-b6ff-4626b52b0b79"
    val DEFAULT_COLUMN_COUNT: Int = 0
    val FULL_WIDTH_COLUMN: Int = 1
    val PICKUP_REF: String = "PickUp"
    val DELIVERY_REF: String = "Delivery"
    val USER_REF: String = "User"
    val USER_CATEGORY: String = "Admin"
    var currentUser: UserModel? = null

}