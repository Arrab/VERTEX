package com.example.mng.vertexdelivery.common

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import com.example.mng.vertexdelivery.model.DeliveryModel
import com.example.mng.vertexdelivery.model.PickUpModel
import com.example.mng.vertexdelivery.model.UserModel
import com.google.android.material.navigation.NavigationView

object Common {



    val COLOR_STATUS_FREE: Int = Color.argb(100,24,90,251)
    val COLOR_STATUS_INPROGRESS: Int = Color.argb(100,255,140,1)
    val COLOR_STATUS_DONE: Int = Color.argb(100,70,251,77)
    val COLOR_STATUS_FINISHED: Int = Color.argb(100,180,180,180)

    var pickupSelected: PickUpModel? = null
    var deliverySelected: DeliveryModel?= null
    var pickupListSelected: List<PickUpModel>? = null
    var deliveryListSelected: List<DeliveryModel>?= null


    val STATUS_DONE: String = "done"
    val STATUS_INPROGRESS: String = "inprogress"
    val STATUS_FREE: String = "free"

    val txt_DONE: String = "Done"
    val txt_INPROGRESS: String = "In Progress"
    val txt_FREE: String = "Free"
    val txt_TAKE_IT: String = "TAKE IT"
    val txt_ITS_DONE: String = "Its Done !!"

    val IMG_DELIV_FREE: String = "https://firebasestorage.googleapis.com/v0/b/vertexdelivery-5c364.appspot.com/o/icons%2Fdelv_free.png?alt=media&token=366768b9-2648-44af-a7e5-e9541580611e"
    val IMG_DELIV_INPROGRESS: String = "https://firebasestorage.googleapis.com/v0/b/vertexdelivery-5c364.appspot.com/o/icons%2Fdelev_progres.png?alt=media&token=2294180c-97be-4be9-b174-e24e542332ac"
    val IMG_DELIV_DONE: String = "https://firebasestorage.googleapis.com/v0/b/vertexdelivery-5c364.appspot.com/o/icons%2Fdelv_done.png?alt=media&token=946e7ad9-e222-4bfe-bcfd-a27183188971"

    val IMG_PICKUP_DONE: String = "https://firebasestorage.googleapis.com/v0/b/vertexdelivery-5c364.appspot.com/o/icons%2Fpickup_done.png?alt=media&token=06a8e230-50db-470e-b74d-1914cce16def"
    val IMG_FREE: String = "https://firebasestorage.googleapis.com/v0/b/vertexdelivery-5c364.appspot.com/o/icons%2Fpickup_free.png?alt=media&token=bb7fab68-22e1-446c-a134-c5f5b534c60c"
    val IMG_PICKUP_INPROGRESS: String = "https://firebasestorage.googleapis.com/v0/b/vertexdelivery-5c364.appspot.com/o/icons%2Fpickup_progress.png?alt=media&token=c3f1bd90-7bf8-40d6-b6ff-4626b52b0b79"

    val DEFAULT_COLUMN_COUNT: Int = 0
    val FULL_WIDTH_COLUMN: Int = 1

    val PICKUP_REF: String = "PickUpDevelop"
    val DELIVERY_REF: String = "DeliveryDevelop"

    val USER_REF: String = "User"
    val USER_CATEGORY_ADMIN: String = "Admin"
    val USER_CATEGORY_DRIVER: String = "Driver"
    var currentUser_id: String? = null
    var currentUser: UserModel? = null
    var userList: List<UserModel>? = null
    var yaEntro = false
    var boolVar = false

    var navlobal: NavigationView?= null
    var navControl: NavController?= null
    var navDrawer: DrawerLayout?= null

    fun setSpanString(
        s: String,
        name: String?,
        txtUser: TextView?
    ) {
        val builder = SpannableStringBuilder()
        builder.append(s)
        val txtSpannable = SpannableStringBuilder(name)
        val boldSpan = StyleSpan(Typeface.BOLD)
        txtSpannable.setSpan(boldSpan, 0,name!!.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.append(txtSpannable)
        txtUser!!.setText(builder, TextView.BufferType.SPANNABLE)
    }

}