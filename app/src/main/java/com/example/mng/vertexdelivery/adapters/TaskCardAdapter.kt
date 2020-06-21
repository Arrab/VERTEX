package com.example.mng.vertexdelivery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mng.vertexdelivery.R
import com.example.mng.vertexdelivery.data.Task
import kotlin.Exception

class TaskCardAdapter(context: Context, resource: Int, list:ArrayList<Task>): ArrayAdapter<Task>(context, resource, list) {

    private var mResource: Int = 0
    private var mList: ArrayList<Task>
    private var mLayoutInflator: LayoutInflater
    private var mContext: Context = context

    init {
        this.mResource = resource
        this.mList = list
        this.mLayoutInflator = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
        val returnView: View
        if(convertView == null){
            returnView = try {
                mLayoutInflator.inflate(mResource,null)
            } catch (e: Exception){
                e.printStackTrace()
                View(context)
            }
            setUI(returnView, position)
            return returnView
        }
        setUI(convertView, position)
        return convertView
    }
    private fun setUI(view: View, position: Int){
        val task: Task? = if (count > position) getItem(position) else null

            val tasID: TextView = view.findViewById(R.id.task_card_id)
            tasID.text = "Task: " + task?.id

            val taskName: TextView? = view.findViewById(R.id.task_card_name)
            taskName?.text = task?.name

            val taskAddress: TextView? = view.findViewById(R.id.task_card_address)
            taskAddress?.text = task?.address

            val taskPhone: TextView? = view.findViewById(R.id.task_card_phone)
            taskPhone?.text = task?.phone

            val taskTime: TextView? = view.findViewById(R.id.task_card_time)
            taskTime?.text = task?.time


    }
}