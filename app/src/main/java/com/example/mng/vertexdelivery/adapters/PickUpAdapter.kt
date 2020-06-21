package com.example.mng.vertexdelivery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.mng.vertexdelivery.R
import com.example.mng.vertexdelivery.callback.IRecycleItemClickListener
import com.example.mng.vertexdelivery.eventBus.PickUpItemClick
import com.example.mng.vertexdelivery.model.PickUpModel
import de.hdodenhof.circleimageview.CircleImageView
import org.greenrobot.eventbus.EventBus

class PickUpAdapter(internal var context:Context, internal var pickUpCategoryModels: List<PickUpModel>):
RecyclerView.Adapter<PickUpAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var pickUp_name: TextView ?= null

        var pickUp_image: CircleImageView ?= null

        internal var listener: IRecycleItemClickListener?= null

        fun setListener(listener: IRecycleItemClickListener){
            this.listener = listener
        }

        init {
            pickUp_name = itemView.findViewById(R.id.txt_home_pickup_name) as TextView
            pickUp_image = itemView.findViewById(R.id.img_home_pickup) as CircleImageView
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener!!.onItemClick(v!!,adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_pick_up_category_item,parent,false))
    }

    override fun getItemCount(): Int {
        return pickUpCategoryModels.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(pickUpCategoryModels[position].image).into(holder.pickUp_image!!)
        holder.pickUp_name!!.text = pickUpCategoryModels[position].name
        holder.setListener(object:IRecycleItemClickListener{
            override fun onItemClick(view: View, pos: Int) {
                EventBus.getDefault().postSticky(PickUpItemClick(pickUpCategoryModels[pos]))
            }

        })
    }
}