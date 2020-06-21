package com.example.mng.vertexdelivery.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.mng.vertexdelivery.R
import com.example.mng.vertexdelivery.adapters.DeliveryAdapter
import com.example.mng.vertexdelivery.adapters.PickUpAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeViewModelDeliv: HomeViewModel


    var recycleView:RecyclerView?=null

    var recyclerViewDeliv:RecyclerView?=null

    var layoutAnimationController: LayoutAnimationController?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModelDeliv= ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initView(root)
        initViewDeliv(root)
        //Bin Data
        homeViewModel.pickUpList.observe(viewLifecycleOwner, Observer {
            val listData = it
            val adapter = PickUpAdapter(requireContext(),listData)
            recycleView!!.adapter = adapter
            recycleView!!.layoutAnimation = layoutAnimationController
        })
        homeViewModelDeliv.deliveryList.observe(viewLifecycleOwner, Observer {
            val listDataDeliv = it
            val adapterDeliv = DeliveryAdapter(requireContext(),listDataDeliv)
            recyclerViewDeliv!!.adapter = adapterDeliv
            recyclerViewDeliv!!.layoutAnimation = layoutAnimationController
        })
        return root
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