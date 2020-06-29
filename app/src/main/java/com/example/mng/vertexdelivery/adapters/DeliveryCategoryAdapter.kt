package com.example.mng.vertexdelivery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mng.vertexdelivery.R
import com.example.mng.vertexdelivery.callback.IRecycleItemClickListener
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.eventBus.DeliveryMenuClick
import com.example.mng.vertexdelivery.model.DeliveryModel
import org.greenrobot.eventbus.EventBus

class DeliveryCategoryAdapter(internal var context: Context, internal var deliveryCategoryList: List<DeliveryModel>):
    RecyclerView.Adapter<DeliveryCategoryAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var delivery_category_invoice: TextView?= null

        internal var listener: IRecycleItemClickListener?= null

        fun setListener(listener: IRecycleItemClickListener){
            this.listener = listener
        }

        init {
            delivery_category_invoice = itemView.findViewById(R.id.txt_delivery_menu_invoice) as TextView
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener!!.onItemClick(v!!,adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_delivery_item,parent,false))
    }

    override fun getItemCount(): Int {
        return deliveryCategoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.delivery_category_invoice!!.setText(deliveryCategoryList.get(position).invoiceNumber)

        //Event
        holder.setListener(object : IRecycleItemClickListener {
            override fun onItemClick(view: View, pos: Int) {
                Common.deliverySelected = deliveryCategoryList.get(pos)
                EventBus.getDefault().postSticky(DeliveryMenuClick(true,deliveryCategoryList.get(pos)))
            }

        })

    }

    override fun getItemViewType(position: Int): Int {
        return if(deliveryCategoryList.size == 1){
            Common.DEFAULT_COLUMN_COUNT
        } else{
            if (deliveryCategoryList.size % 2 == 0){
                Common.DEFAULT_COLUMN_COUNT
            } else{
                if (position > 1 && position == deliveryCategoryList.size - 1){
                    Common.FULL_WIDTH_COLUMN
                } else{
                    Common.DEFAULT_COLUMN_COUNT
                }
            }
        }
        return super.getItemViewType(position)
    }
}