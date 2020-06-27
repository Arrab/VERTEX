package com.example.mng.vertexdelivery.ui.details_pick_up

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mng.vertexdelivery.R
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.model.DeliveryModel
import com.example.mng.vertexdelivery.model.PickUpModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import dmax.dialog.SpotsDialog
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class PickUpDetailsFragment : Fragment() {

    private lateinit var pickupDetailsViewModel: PickUpDetailsViewModel

    private var name_details: TextView? = null
    private var time_details: TextView? = null
    private var description_details: TextView? = null
    private var address_details: TextView? = null
    private var phone_details: TextView? = null
    private var date_details: TextView? = null
    private var status_title: TextView? = null
    private var btn_done_details: Button? = null
    private var btn_edit_description: FloatingActionButton? = null

    private var waitingDialog: AlertDialog? = null
    val btnstatusModel:PickUpModel?= Common.pickupSelected


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pickupDetailsViewModel =
            ViewModelProviders.of(this).get(PickUpDetailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pickup_details, container, false)
        initViews(root)

        pickupDetailsViewModel.getMutableLiveData().observe(viewLifecycleOwner, Observer {
            displayInfo(it)
        })

        return root
    }

    private fun submitToFirebase(it: PickUpModel?) {
        waitingDialog!!.show()
        val data = FirebaseDatabase.getInstance().getReference(Common.PICKUP_REF)
        val data2 = data.child(Common.pickupSelected!!.task_id!!)
        data2.child("status").setValue(it!!.status)
        data2.child("dateOperation").setValue(it!!.dateOperation)
        data2.child("image").setValue(it!!.image)
        waitingDialog!!.dismiss()
    }

    private fun submitToFirebaseDescription(it: PickUpModel?) {
        waitingDialog!!.show()
        val data = FirebaseDatabase.getInstance().getReference(Common.PICKUP_REF)
        val data2 = data.child(Common.pickupSelected!!.task_id!!)
        data2.child("description").setValue(it!!.description)
        waitingDialog!!.dismiss()
    }

    private fun displayInfo(it: PickUpModel?) {
        name_details!!.text = StringBuilder(it!!.name!!)
        time_details!!.text = StringBuilder(it!!.time!!)
        description_details!!.text = StringBuilder(it!!.description!!)
        address_details!!.text = StringBuilder(it!!.address!!)
        phone_details!!.text = StringBuilder(it!!.phone!!)
        date_details!!.text = StringBuilder(it!!.date.toString())
        wichStatus(it)
        if (btnstatusModel!!.status == Common.STATUS_DONE) {
            btn_done_details!!.isClickable = false
            btn_edit_description!!.isClickable = false
        }

    }

    private fun wichStatus(it: PickUpModel) {
        if (it.status == Common.STATUS_FREE){
            status_title!!.setText(Common.txt_FREE)
            status_title!!.setBackgroundColor(Common.COLOR_STATUS_FREE)
        } else if (it.status == Common.STATUS_INPROGRESS){
            status_title!!.setText(Common.txt_INPROGRESS)
            status_title!!.setBackgroundColor(Common.COLOR_STATUS_INPROGRESS)
        } else if (it.status == Common.STATUS_DONE){
            status_title!!.setText(Common.txt_DONE)
            status_title!!.setBackgroundColor(Common.COLOR_STATUS_DONE)
        }
    }
    private fun setStatusBoton() {
        if (btnstatusModel!!.status == Common.STATUS_FREE){
            btn_done_details!!.setText(Common.txt_TAKE_IT)
            btn_done_details!!.setBackgroundColor(Common.COLOR_STATUS_INPROGRESS)
            btn_done_details!!.isClickable = true
        } else if (btnstatusModel!!.status == Common.STATUS_INPROGRESS){
            btn_done_details!!.setText(Common.txt_DONE)
            btn_done_details!!.setBackgroundColor(Common.COLOR_STATUS_DONE)
            btn_done_details!!.setTextColor(Color.BLACK)
            btn_done_details!!.isClickable = true
        }else if (btnstatusModel!!.status == Common.STATUS_DONE){
            btn_done_details!!.setText(Common.txt_ITS_DONE)
            btn_done_details!!.isClickable = false
            btn_done_details!!.setBackgroundColor(Common.COLOR_STATUS_FINISHED)
            btn_done_details!!.setTextColor(Color.BLACK)
        }
        wichStatus(btnstatusModel!!)
    }

    private fun wichStatusClicked(statusModel: PickUpModel?, fecha: String) {
        if (statusModel!!.status == Common.STATUS_FREE) {
            statusModel!!.status = Common.STATUS_INPROGRESS
            statusModel!!.dateOperation += "----. ${Common.STATUS_INPROGRESS}: ${fecha}"
            statusModel!!.image = Common.IMG_PICKUP_INPROGRESS
            btn_done_details!!.setText(Common.txt_DONE)
            btn_done_details!!.setBackgroundColor(Common.COLOR_STATUS_DONE)
            btn_done_details!!.setTextColor(Color.BLACK)
        } else if (statusModel!!.status == Common.STATUS_INPROGRESS) {
            statusModel!!.status = Common.STATUS_DONE
            statusModel!!.dateOperation += "--. ${Common.STATUS_DONE}: ${fecha}"
            statusModel!!.image = Common.IMG_PICKUP_DONE
            btn_done_details!!.setText(Common.txt_ITS_DONE)
            btn_done_details!!.isClickable = false
            btn_done_details!!.setBackgroundColor(Common.COLOR_STATUS_FINISHED)
            btn_done_details!!.setTextColor(Color.BLACK)
        }
        wichStatus(statusModel!!)
        pickupDetailsViewModel.setStatusModel(statusModel!!)
    }



    @SuppressLint("ResourceAsColor")
    private fun initViews(root: View?) {
        waitingDialog =
            SpotsDialog.Builder().setContext(requireContext()).setCancelable(false).build()

        btn_done_details = root!!.findViewById(R.id.btn_status_details) as Button
        name_details = root!!.findViewById(R.id.pickup_name_details) as TextView
        time_details = root!!.findViewById(R.id.time_details_pickup) as TextView
        description_details = root!!.findViewById(R.id.txt_pickup_details_description) as TextView
        address_details = root!!.findViewById(R.id.address_details_pickup) as TextView
        phone_details = root!!.findViewById(R.id.phone_details_pickup) as TextView
        date_details = root!!.findViewById(R.id.date_details_pickup) as TextView
        status_title = root!!.findViewById(R.id.txt_pickup_status_details)
        btn_edit_description = root!!.findViewById(R.id.btn_pickup_edit_description) as FloatingActionButton

        setStatusBoton()
        wichStatus(btnstatusModel!!)

        btn_done_details!!.setOnClickListener {
            showDialogStatus()
            pickupDetailsViewModel.getMutableStatusLiveData().observe(viewLifecycleOwner, Observer {
                submitToFirebase(it)
            })
        }
        btn_edit_description!!.setOnClickListener {
            editPickupDescription()
            pickupDetailsViewModel.getMutableStatusLiveData().observe(viewLifecycleOwner, Observer {
                submitToFirebaseDescription(it)
                displayInfo(it)
            })
        }
    }

    private fun editPickupDescription() {
        if (btnstatusModel!!.status != Common.STATUS_DONE) {
            var builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Edit description")

            val itemView =
                LayoutInflater.from(context).inflate(R.layout.layout_pickup_edit_descrption, null)
            var txt_description: EditText = itemView.findViewById(R.id.txt_pickup_edit_descrption)
            txt_description.setText(btnstatusModel!!.description.toString())
            builder.setView(itemView)
            builder.setNegativeButton("CANCEL") { dialogInterface, i -> dialogInterface.dismiss() }
            builder.setPositiveButton("OK") { dialogInterface, i ->
                val statusModel: PickUpModel? = Common.pickupSelected
//                statusModel!!.description += "---.Text Edited on status: ${statusModel!!.status}:-- ${txt_description.toString()}"
                statusModel!!.description = txt_description.text.toString()
                pickupDetailsViewModel.setStatusModel(statusModel!!)
            }
            val dialog = builder.create()
            dialog.show()
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

    @SuppressLint("NewApi", "ResourceAsColor")
    private fun showDialogStatus() {
        var builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Set Status of Task")

        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_pickup_satatus, null)
        builder.setView(itemView)
        builder.setNegativeButton("CANCEL") { dialogInterface, i -> dialogInterface.dismiss() }
        builder.setPositiveButton("OK") { dialogInterface, i ->
            val statusModel:PickUpModel?= Common.pickupSelected
            val fecha = dateOfZone()
            wichStatusClicked(statusModel,fecha)
        }
        val dialog = builder.create()
        dialog.show()
    }




}