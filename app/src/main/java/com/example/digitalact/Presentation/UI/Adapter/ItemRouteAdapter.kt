package com.example.digitalact.Presentation.UI.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalact.Domains.Model.Task
import com.example.digitalact.R

class ItemRouteAdapter(val context: Context?, private val itemList: MutableList<Task>) : RecyclerView.Adapter<ItemRouteAdapter.MyViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_route, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.addressTextView.text = itemList[position].address
        holder.fullNameTextView.text = itemList[position].fullName
        holder.telephoneNumberTextView.text = itemList[position].telephone
        holder.typePUTextView.text = itemList[position].typePU
        holder.numberPUTextView.text = itemList[position].numberPU
        holder.descriptionTextView.text = itemList[position].comment

    }

    override fun getItemCount(): Int = itemList.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addressTextView = itemView.findViewById<TextView>(R.id.addressTextView)
        val fullNameTextView = itemView.findViewById<TextView>(R.id.fullNameTextView)
        val telephoneNumberTextView = itemView.findViewById<TextView>(R.id.telephoneNumberTextView)
        val typePUTextView = itemView.findViewById<TextView>(R.id.typePUTextView)
        val numberPUTextView = itemView.findViewById<TextView>(R.id.numberPUTextView)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.commentTextView)
    }
}