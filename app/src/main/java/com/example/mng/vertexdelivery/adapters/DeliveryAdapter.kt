package com.example.mng.vertexdelivery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mng.vertexdelivery.R
import com.example.mng.vertexdelivery.model.DeliveryModel
import de.hdodenhof.circleimageview.CircleImageView

class DeliveryAdapter (internal var context: Context, internal var deliveryCategoryModels: List<DeliveryModel>):
RecyclerView.Adapter<DeliveryAdapter.MyViewHolder>(){
    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var delivery_name: TextView?= null
        var delivery_image: CircleImageView?= null
        init {
            delivery_name = itemView.findViewById(R.id.txt_category_delivery_name) as TextView
            delivery_image = itemView.findViewById(R.id.category_delivery_image) as CircleImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_delivery_category_item,parent,false))
    }

    override fun getItemCount(): Int {
        return deliveryCategoryModels.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(deliveryCategoryModels.get(position).image).into(holder.delivery_image!!)
        holder.delivery_name!!.setText(deliveryCategoryModels.get(position).name)
    }
}