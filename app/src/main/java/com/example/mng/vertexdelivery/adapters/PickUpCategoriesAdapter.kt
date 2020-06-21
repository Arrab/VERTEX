package com.example.mng.vertexdelivery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mng.vertexdelivery.R
import com.example.mng.vertexdelivery.callback.IRecycleItemClickListener
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.eventBus.CategoryClick
import com.example.mng.vertexdelivery.model.PickUpModel
import org.greenrobot.eventbus.EventBus

class PickUpCategoriesAdapter(internal var context: Context, internal var pickUpCategoryList: List<PickUpModel>):
    RecyclerView.Adapter<PickUpCategoriesAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var pickUp_category_name: TextView?= null

        var pickUp_category_time: TextView?= null

       // var pick_category_image: ImageView?= null

        internal var listener: IRecycleItemClickListener?= null

        fun setListener(listener:IRecycleItemClickListener){
            this.listener = listener
        }

        init {
            pickUp_category_name = itemView.findViewById(R.id.txt_pickup_menu_name) as TextView
            pickUp_category_time = itemView.findViewById(R.id.txt_pickup_menu_time) as TextView
            //pick_category_image = itemView.findViewById(R.id.img_category_pickup) as ImageView
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener!!.onItemClick(v!!,adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_pickup_item,parent,false))
    }

    override fun getItemCount(): Int {
        return pickUpCategoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //Glide.with(context).load(pickUpCategoryList.get(position).image).into(holder.pick_category_image!!)
        holder.pickUp_category_name!!.setText(pickUpCategoryList.get(position).name)
        holder.pickUp_category_time!!.setText(pickUpCategoryList.get(position).time)

        //Event
        holder.setListener(object :IRecycleItemClickListener{
            override fun onItemClick(view: View, pos: Int) {
                Common.pickupSelected = pickUpCategoryList.get(pos)
                EventBus.getDefault().postSticky(CategoryClick(true,pickUpCategoryList.get(pos)))
            }

        })

    }

    override fun getItemViewType(position: Int): Int {
        return if(pickUpCategoryList.size == 1){
            Common.DEFAULT_COLUMN_COUNT
        } else{
            if (pickUpCategoryList.size % 2 == 0){
                Common.DEFAULT_COLUMN_COUNT
            } else{
                if (position > 1 && position == pickUpCategoryList.size - 1){
                    Common.FULL_WIDTH_COLUMN
                } else{
                    Common.DEFAULT_COLUMN_COUNT
                }
            }
        }
        return super.getItemViewType(position)
    }
}