package com.example.mng.vertexdelivery.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mng.vertexdelivery.HomeActivity
import com.example.mng.vertexdelivery.LoginActivity

import com.example.mng.vertexdelivery.R
import com.example.mng.vertexdelivery.adapters.DeliveryAdapter
import com.example.mng.vertexdelivery.adapters.PickUpAdapter
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.model.DeliveryModel
import com.example.mng.vertexdelivery.model.PickUpModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.net.CookieHandler

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeViewModelDeliv: HomeViewModel
    private lateinit var homActivity: HomeActivity
    private lateinit var userViewActual: HomeViewModel


    var recycleView:RecyclerView?=null

    var recyclerViewDeliv:RecyclerView?=null

    var layoutAnimationController: LayoutAnimationController?= null
    private var boton_refresh:FloatingActionButton?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModelDeliv= ViewModelProviders.of(this).get(HomeViewModel::class.java)
        userViewActual = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homActivity = HomeActivity()
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val root2 =inflater.inflate(R.layout.activity_home,container,false)
        initView(root)
        initViewDeliv(root)

        //Bin Data
        userViewActual.getUserActual().observe(viewLifecycleOwner, Observer {
            val intent = Intent(context, HomeActivity::class.java)
            homActivity.CargarDatosUsuario(intent)
            if (!Common.boolVar){
                Toast.makeText(context,"Access not allowed, you are NOT Admin!!",Toast.LENGTH_LONG).show()
                startActivity(Intent(context,LoginActivity::class.java))
            }
        })


        homeViewModel.getHomePickUpList().observe(viewLifecycleOwner, Observer {
            displayPickUpInfo(it)
        })
        homeViewModelDeliv.getHomeDeliveryList().observe(viewLifecycleOwner, Observer {
            displayDeliveryInfo(it)
        })
        boton_refresh = root.findViewById(R.id.btn_refresh)
        boton_refresh!!.setOnClickListener {
            refreshPickUpLista()
            refreshDeliveryLista()
            val statusListPickModel: List<PickUpModel>? = Common.pickupListSelected
            val statusDelivModel: List<DeliveryModel>? = Common.deliveryListSelected
            homeViewModel.setHomePickUpList(statusListPickModel!!)
            homeViewModel.getHomePickUpList().observe(viewLifecycleOwner, Observer {
                displayPickUpInfo(it)
            })
            homeViewModelDeliv.setHomeDeliveryList(statusDelivModel!!)
            homeViewModelDeliv.getHomeDeliveryList().observe(viewLifecycleOwner, Observer {
                displayDeliveryInfo(it)
            })
        }

        return root
    }


    private fun refreshDeliveryLista() {
        val list = mutableListOf<DeliveryModel>()
        val statusListDelivModel: List<DeliveryModel>? = Common.deliveryListSelected
        val statusDelivModel: DeliveryModel?= Common.deliverySelected
        if ((statusListDelivModel != null) && (statusDelivModel != null)) {
            for (p0 in statusListDelivModel!!) {
                if (p0!!.package_id == statusDelivModel!!.package_id) {
                    list.add(statusDelivModel)
                }else {
                    list.add(p0)
                }
            }
            Common.deliveryListSelected = list
        }
    }

    private fun refreshPickUpLista() {
        val list = mutableListOf<PickUpModel>()
        val statusListPickModel: List<PickUpModel>? = Common.pickupListSelected
        val statusPickModel: PickUpModel?= Common.pickupSelected
        if ((statusListPickModel != null) && (statusPickModel != null)) {
            for (p0 in statusListPickModel!!) {
                if (p0!!.task_id == statusPickModel!!.task_id) {
                    list.add(statusPickModel)
                }else {
                    list.add(p0)
                }
            }
            Common.pickupListSelected = list
        }
    }

    private fun displayPickUpInfo(it: List<PickUpModel>?) {
        val listData = it
        val adapter = PickUpAdapter(requireContext(),listData!!)
        recycleView!!.adapter = adapter
        recycleView!!.layoutAnimation = layoutAnimationController
    }

    private fun displayDeliveryInfo(it: List<DeliveryModel>?) {
        val listDataDeliv = it
        val adapterDeliv = DeliveryAdapter(requireContext(),listDataDeliv!!)
        recyclerViewDeliv!!.adapter = adapterDeliv
        recyclerViewDeliv!!.layoutAnimation = layoutAnimationController

    }

    private fun initView(root:View) {
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_item_from_left)
        recycleView = root.findViewById(R.id.recycler_pickup) as RecyclerView
        recycleView!!.setHasFixedSize(true)
        recycleView!!.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL, false)

    }

    private fun initViewDeliv(root:View) {
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_item_from_left)
        recyclerViewDeliv = root.findViewById(R.id.recycler_delivery) as RecyclerView
        recyclerViewDeliv!!.setHasFixedSize(true)
        recyclerViewDeliv!!.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL, false)

    }
}