package com.example.digitalact.Presentation.UI.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalact.Domains.Model.Act
import com.example.digitalact.R

class ItemActAdapter(val context: Context?, private var actList: MutableList<Act?>, private val listener: ActClickListener) : RecyclerView.Adapter<ItemActAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_act, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(actList[position] != null) {
           holder.accountNumberTextView.text = actList[position]?.accountNumber
           holder.addressTextView.text = actList[position]?.address
           holder.reasonTextView.text = actList[position]?.reasonReplacement
            holder.dateOfCorrectionTextView.text = actList[position]?.dateCompletion
        }

        holder.itemView.setOnClickListener(View.OnClickListener {
            listener.onActClicked(actList[position])
        })

    }

    override fun getItemCount() = actList.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val accountNumberTextView: TextView = itemView.findViewById(R.id.addressTextView)
        val addressTextView: TextView = itemView.findViewById(R.id.fullNameTextView)
        val reasonTextView: TextView = itemView.findViewById(R.id.telephoneNumberTextView)
        val dateOfCorrectionTextView: TextView = itemView.findViewById(R.id.typePUTextView)

    }

}

interface ActClickListener {
    fun onActClicked(act: Act?)
}