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
import com.example.mng.vertexdelivery.adapters.PickUpCategoriesAdapter
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.common.SpacesItemDecoration
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
    private var adapter: PickUpCategoriesAdapter? = null
    private var recycle_menu_pickup_var: RecyclerView? = null
    private var btn_create: FloatingActionButton? = null

    private var waitingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuViewModel =
            ViewModelProviders.of(this).get(MenuDeliveryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pickup, container, false)


        initViews(root)

        menuViewModel.getMessageError().observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        menuViewModel.getCategoryList().observe(viewLifecycleOwner, Observer {
            dialog.dismiss()
            val adapter = PickUpCategoriesAdapter(requireContext(), it)
            recycle_menu_pickup_var!!.adapter = adapter
            recycle_menu_pickup_var!!.layoutAnimation = layoutAnimationController
            Common.pickupSelected = it.get(0)
        })

        return root
    }

    private fun initViews(root: View) {
        waitingDialog =SpotsDialog.Builder().setContext(requireContext()).setCancelable(false).build()
        dialog = SpotsDialog.Builder().setContext(context).setCancelable(false).build()
        dialog.show()
        btn_create = root!!.findViewById(R.id.btn_pickup_menu_create) as FloatingActionButton
        layoutAnimationController =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_item_from_left)
        recycle_menu_pickup_var = root.findViewById(R.id.recycle_menu_pickup) as RecyclerView
        recycle_menu_pickup_var!!.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.orientation = RecyclerView.VERTICAL

        recycle_menu_pickup_var!!.layoutManager = layoutManager
        recycle_menu_pickup_var!!.addItemDecoration(SpacesItemDecoration(8))



        btn_create!!.setOnClickListener {
            showDialogStatus(root)
        }
    }

    private fun submitToFirebase(it: PickUpModel?) {
        waitingDialog!!.show()
        var count: Long = 0
        val pickUpRef = FirebaseDatabase.getInstance().getReference(Common.PICKUP_REF)
        pickUpRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                menuViewModel.onMenuPickUpLoadFaild(p0.message!!)
            }
            override fun onDataChange(p0: DataSnapshot) {
                count = p0.childrenCount
                it!!.task_id = "task_${count}"
                val data2 = pickUpRef.child(it!!.task_id!!)
                data2.child("address").setValue(it!!.address)
                data2.child("date").setValue(it!!.date)
                data2.child("dateOperation").setValue(it!!.dateOperation)
                data2.child("description").setValue(it!!.description)
                data2.child("image").setValue(it!!.image)
                data2.child("name").setValue(it!!.name)
                data2.child("phone").setValue(it!!.phone)
                data2.child("status").setValue(it!!.status)
                data2.child("task_id").setValue(it!!.task_id)
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

        builder.setTitle("Create new Task")

        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_pickup_create_task, null)
        val txtName_create = itemView.findViewById<EditText>(R.id.txtName_pickup_create_task)
        val txtAddress_create = itemView.findViewById<EditText>(R.id.txtAddress_pickup_create_task)
        val txtPhone_create = itemView.findViewById<EditText>(R.id.txtphone_pickup_create_task)
        val txtDescriptio_create = itemView.findViewById<EditText>(R.id.txtDescription_pickup_create_task)
        val txtTime_create = itemView.findViewById<EditText>(R.id.txtTime_pickup_create_task)
        builder.setView(itemView)
        builder.setNegativeButton("CANCEL") { dialogInterface, i -> dialogInterface.dismiss() }
        builder.setPositiveButton("CREATE") { dialogInterface, i ->
            if (Common.pickupSelected == null) {
                Toast.makeText(context,"Queeeeeeeeeeeeeeeeeeeeee",Toast.LENGTH_SHORT).show()
            }
            val statusModel: PickUpModel?= Common.pickupSelected
            statusModel!!.dateOperation = formatter.format(currentTime).toString()
            statusModel!!.date = formatter2.format(currentTime).toString()
            statusModel!!.status = Common.STATUS_FREE
            statusModel!!.address = txtAddress_create!!.text.toString()
            statusModel!!.description = txtDescriptio_create!!.text.toString()
            statusModel!!.image = Common.IMG_FREE
            statusModel!!.name = txtName_create!!.text.toString().toUpperCase()
            statusModel!!.phone = txtPhone_create!!.text.toString()
            statusModel!!.time = txtTime_create!!.text.toString()
            menuViewModel.setCreateModel(statusModel!!)
            menuViewModel.getMutableCreateLiveData().observe(viewLifecycleOwner, Observer {
                submitToFirebase(it)
            })
        }
        val dialog = builder.create()
        dialog.show()

    }


}