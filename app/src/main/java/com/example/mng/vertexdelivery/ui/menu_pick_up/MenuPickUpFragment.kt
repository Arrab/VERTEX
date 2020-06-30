package com.example.mng.vertexdelivery.ui.menu_pick_up

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
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

class MenuPickUpFragment : Fragment() {
    private lateinit var dialog: AlertDialog
    private lateinit var layoutAnimationController: LayoutAnimationController
    private lateinit var menuViewModel: MenuPickUpViewModel
    private var adapter: PickUpCategoriesAdapter? = null
    private var recycle_menu_pickup_var: RecyclerView? = null
   // private var btn_create: FloatingActionButton? = null

    private var waitingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuViewModel =
            ViewModelProviders.of(this).get(MenuPickUpViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pickup, container, false)
        initViews(root)
        menuViewModel.getMessageError().observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
        menuViewModel.getCategoryList().observe(viewLifecycleOwner, Observer {
           displayInfo(it)
        })
        return root
    }

    private fun displayInfo(it: List<PickUpModel>?) {
        dialog.dismiss()
        val adapter = PickUpCategoriesAdapter(requireContext(), it!!)
        recycle_menu_pickup_var!!.adapter = adapter
        recycle_menu_pickup_var!!.layoutAnimation = layoutAnimationController
        //Common.pickupSelected = it!!.get(0)
    }

    private fun initViews(root: View) {
        waitingDialog =
            SpotsDialog.Builder().setContext(requireContext()).setCancelable(false).build()
        dialog = SpotsDialog.Builder().setContext(context).setCancelable(false).build()
        dialog.show()
        // btn_create = root!!.findViewById(R.id.btn_pickup_menu_create) as FloatingActionButton
        layoutAnimationController =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_item_from_left)
        recycle_menu_pickup_var = root.findViewById(R.id.recycle_menu_pickup) as RecyclerView
        recycle_menu_pickup_var!!.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.orientation = RecyclerView.VERTICAL

        recycle_menu_pickup_var!!.layoutManager = layoutManager
        recycle_menu_pickup_var!!.addItemDecoration(SpacesItemDecoration(8))


    }


}