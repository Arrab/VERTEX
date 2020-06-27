package com.example.mng.vertexdelivery.ui.menu_delivery

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mng.vertexdelivery.R
import com.example.mng.vertexdelivery.adapters.DeliveryCategoryAdapter
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.common.SpacesItemDecoration
import com.example.mng.vertexdelivery.model.DeliveryModel
import com.example.mng.vertexdelivery.model.PickUpModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import dmax.dialog.SpotsDialog
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MenuDeliveryFragment : Fragment() {
    private lateinit var dialog: AlertDialog
    private lateinit var layoutAnimationController: LayoutAnimationController
    private lateinit var menuViewModel: MenuDeliveryViewModel
    private var adapter: DeliveryCategoryAdapter? = null
    private var recycle_menu_delivery_var: RecyclerView? = null
    private var btn_create: FloatingActionButton? = null

    private var waitingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuViewModel =
            ViewModelProviders.of(this).get(MenuDeliveryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_delivery, container, false)
        initViews(root)
        menuViewModel.getMessageError().observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
        menuViewModel.getCategoryList().observe(viewLifecycleOwner, Observer {
            displayInfo(it)
        })
        return root
    }

    private fun displayInfo(it: List<DeliveryModel>?) {
        dialog.dismiss()
        val adapter = DeliveryCategoryAdapter(requireContext(), it!!)
        recycle_menu_delivery_var!!.adapter = adapter
        recycle_menu_delivery_var!!.layoutAnimation = layoutAnimationController
        //Common.deliverySelected = it!!.get(0)
    }


    private fun initViews(root: View) {
        waitingDialog =SpotsDialog.Builder().setContext(requireContext()).setCancelable(false).build()
        dialog = SpotsDialog.Builder().setContext(context).setCancelable(false).build()
        dialog.show()
        btn_create = root!!.findViewById(R.id.btn_delivery_menu_create) as FloatingActionButton
        layoutAnimationController =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_item_from_left)
        recycle_menu_delivery_var = root.findViewById(R.id.recycle_menu_delivery) as RecyclerView
        recycle_menu_delivery_var!!.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.orientation = RecyclerView.VERTICAL

        recycle_menu_delivery_var!!.layoutManager = layoutManager
        recycle_menu_delivery_var!!.addItemDecoration(SpacesItemDecoration(8))

        btn_create!!.setOnClickListener {
            showDialogStatus(root)

        }
    }

    private fun submitToFirebase(it: DeliveryModel?) {
        waitingDialog!!.show()
        var count: Long = 0
        val delivpRef = FirebaseDatabase.getInstance().getReference(Common.DELIVERY_REF)
        delivpRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                menuViewModel.onMenuDeliveryLoadFaild(p0.message!!)
            }
            override fun onDataChange(p0: DataSnapshot) {
                count = p0.childrenCount
                it!!.package_id = count.toString()
                val data2 = delivpRef.child(it!!.package_id!!)
                data2.child("address").setValue(it!!.address)
                data2.child("date").setValue(it!!.date)
                data2.child("dateOperation").setValue(it!!.dateOperation)
                data2.child("description").setValue(it!!.description)
                data2.child("image").setValue(it!!.image)
                data2.child("name").setValue(it!!.name)
                data2.child("phone").setValue(it!!.phone)
                data2.child("status").setValue(it!!.status)
                data2.child("package_id").setValue(it!!.package_id)
                data2.child("time").setValue(it!!.time)
                waitingDialog!!.dismiss()
            }
        })
    }
    @SuppressLint("NewApi")
    private fun showDialogStatus(root: View) {
        var builder = AlertDialog.Builder(requireContext())

        val fromTimeZone =ZoneId.of("Africa/Cairo") //Zona horaria
        val today = LocalDateTime.now() //fecha actual
        val currentTime = today.atZone(fromTimeZone)
        val DATE_FORMAT = "yyyy/MM/dd HH:mm:ss"
        val DATE_FORMAT2 = "yyyy/MM/dd"
        val formatter =DateTimeFormatter.ofPattern(DATE_FORMAT)
        val formatter2 =DateTimeFormatter.ofPattern(DATE_FORMAT2)

        builder.setTitle("Create new Package")

        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_delivery_create_task, null)
        val txtName_create = itemView.findViewById<EditText>(R.id.txtName_delivery_create_task)
        val txtAddress_create = itemView.findViewById<EditText>(R.id.txtAddress_delivery_create_task)
        val txtPhone_create = itemView.findViewById<EditText>(R.id.txtphone_delivery_create_task)
        val txtDescriptio_create = itemView.findViewById<EditText>(R.id.txtDescription_delivery_create_task)
        val txtTime_create = itemView.findViewById<EditText>(R.id.txtTime_delivery_create_task)
        builder.setView(itemView)
        builder.setNegativeButton("CANCEL") { dialogInterface, i -> dialogInterface.dismiss() }
        builder.setPositiveButton("CREATE") { dialogInterface, i ->
//            if (Common.deliverySelected == null) {
//                Toast.makeText(context,"Error on creating delivery",Toast.LENGTH_SHORT).show()
//            }
            val statusModel: DeliveryModel?= DeliveryModel()
            statusModel!!.dateOperation = formatter.format(currentTime).toString()
            statusModel!!.date = formatter2.format(currentTime).toString()
            statusModel!!.status = Common.STATUS_FREE
            statusModel!!.address = txtAddress_create!!.text.toString()
            statusModel!!.description = txtDescriptio_create!!.text.toString()
            statusModel!!.image = Common.IMG_DELIV_FREE
            statusModel!!.name = txtName_create!!.text.toString().toUpperCase()
            statusModel!!.phone = txtPhone_create!!.text.toString()
            statusModel!!.time = txtTime_create!!.text.toString()
            Common.deliverySelected = statusModel
            menuViewModel.setCreateModel(statusModel!!)
            menuViewModel.getMutableCreateLiveData().observe(viewLifecycleOwner, Observer {
                submitToFirebase(it)
            })
            menuViewModel.setCategoryList(statusModel)
            menuViewModel.getCategoryList().observe(viewLifecycleOwner, Observer {
                displayInfo(it)
            })
        }
        val dialog = builder.create()
        dialog.show()
    }


}


