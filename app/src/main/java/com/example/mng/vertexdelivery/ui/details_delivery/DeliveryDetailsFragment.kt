package com.example.mng.vertexdelivery.ui.details_delivery

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mng.vertexdelivery.R
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.model.DeliveryModel
import com.google.firebase.database.FirebaseDatabase
import dmax.dialog.SpotsDialog
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DeliveryDetailsFragment : Fragment() {

    private lateinit var deliveryDetailsViewModel: DeliveryDetailsViewModel

    private var name_details: TextView? = null
    private var time_details: TextView? = null
    private var description_details: TextView? = null
    private var address_details: TextView? = null
    private var phone_details: TextView? = null
    private var date_details: TextView? = null
    private var btn_done_details: Button? = null

    private var waitingDialog: AlertDialog? = null
    val btnstatusModel:DeliveryModel?= Common.deliverySelected

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        deliveryDetailsViewModel = ViewModelProviders.of(this).get(DeliveryDetailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_delivery_details, container, false)
        initViews(root)

        deliveryDetailsViewModel.getMutableLiveData().observe(viewLifecycleOwner, Observer {
            displayInfo(it)
        })



        return root
    }

    private fun submitToFirebase(it: DeliveryModel?) {
        waitingDialog!!.show()
        val data = FirebaseDatabase.getInstance().getReference(Common.DELIVERY_REF)
        val data2 = data.child(Common.deliverySelected!!.package_id!!)
        data2.child("status").setValue(it!!.status)
        data2.child("dateOperation").setValue(it!!.dateOperation)
        data2.child("image").setValue(it!!.image)
        waitingDialog!!.dismiss()
    }

    private fun displayInfo(it: DeliveryModel?) {
        name_details!!.text = StringBuilder(it!!.name!!)
        time_details!!.text = StringBuilder(it!!.time!!)
        description_details!!.text = StringBuilder(it!!.description!!)
        address_details!!.text = StringBuilder(it!!.address!!)
        phone_details!!.text = StringBuilder(it!!.phone!!)
        date_details!!.text = StringBuilder(it!!.date.toString())
        if (btnstatusModel!!.status == Common.STATUS_DONE)
            btn_done_details!!.isClickable = false

    }


    private fun initViews(root: View?) {
        waitingDialog =
            SpotsDialog.Builder().setContext(requireContext()).setCancelable(false).build()

        btn_done_details = root!!.findViewById(R.id.btn_status_details_delivery) as Button
        name_details = root!!.findViewById(R.id.delivery_name_details) as TextView
        time_details = root!!.findViewById(R.id.time_details_delivery) as TextView
        description_details = root!!.findViewById(R.id.txt_delivery_details_description) as TextView
        address_details = root!!.findViewById(R.id.address_details_delivery) as TextView
        phone_details = root!!.findViewById(R.id.phone_details_delivery) as TextView
        date_details = root!!.findViewById(R.id.date_details_delivery) as TextView
        if (btnstatusModel!!.status == Common.STATUS_FREE){
            btn_done_details!!.setText(Common.txt_FREE)
            btn_done_details!!.setBackgroundColor(Color.BLUE)
        } else if (btnstatusModel!!.status == Common.STATUS_INPROGRESS){
            btn_done_details!!.setText(Common.txt_INPROGRESS)
            btn_done_details!!.setBackgroundColor(Color.YELLOW)
            btn_done_details!!.setTextColor(Color.BLACK)
        }else if (btnstatusModel!!.status == Common.STATUS_DONE){
            btn_done_details!!.setText(Common.txt_DONE)
            btn_done_details!!.isClickable = false
            btn_done_details!!.setBackgroundColor(Color.GREEN)
            btn_done_details!!.setTextColor(Color.BLACK)
        }

        btn_done_details!!.setOnClickListener {
            showDialogStatus()
            deliveryDetailsViewModel.getMutableStatusLiveData().observe(viewLifecycleOwner, Observer {
                submitToFirebase(it)
            })
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateOfZone():String{
        val fromTimeZone =ZoneId.of("Africa/Cairo") //Zona horaria
        val today = LocalDateTime.now() //fecha actual
        val currentTime = today.atZone(fromTimeZone)
        val DATE_FORMAT = "yyyy/MM/dd HH:mm:ss"
        val formatter =DateTimeFormatter.ofPattern(DATE_FORMAT)
        return formatter.format(currentTime).toString()
    }

    @SuppressLint("NewApi")
    private fun showDialogStatus() {
        var builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Set Status of Task Delivery")

        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_delivery_status, null)
        builder.setView(itemView)
        builder.setNegativeButton("CANCEL") { dialogInterface, i -> dialogInterface.dismiss() }
        builder.setPositiveButton("OK") { dialogInterface, i ->
            val statusModel:DeliveryModel?= Common.deliverySelected
            val fecha = dateOfZone()
            if (statusModel!!.status == Common.STATUS_FREE) {
                statusModel!!.status = Common.STATUS_INPROGRESS
                statusModel!!.dateOperation += "--. ${Common.STATUS_INPROGRESS}: ${fecha}"
                statusModel!!.image = Common.IMG_DELIV_INPROGRESS
                btn_done_details!!.setText(Common.txt_INPROGRESS)
                btn_done_details!!.setBackgroundColor(Color.YELLOW)
                btn_done_details!!.setTextColor(Color.BLACK)
            } else if (statusModel!!.status == Common.STATUS_INPROGRESS) {
                statusModel!!.status = Common.STATUS_DONE
                statusModel!!.dateOperation += "--. ${Common.STATUS_DONE}: ${fecha}"
                statusModel!!.image = Common.IMG_DELIV_DONE
                btn_done_details!!.setText(Common.txt_DONE)
                btn_done_details!!.isClickable = false
                btn_done_details!!.setBackgroundColor(Color.GREEN)
                btn_done_details!!.setTextColor(Color.BLACK)
            }
            deliveryDetailsViewModel.setStatusModel(statusModel!!)
        }
        val dialog = builder.create()
        dialog.show()



    }


}