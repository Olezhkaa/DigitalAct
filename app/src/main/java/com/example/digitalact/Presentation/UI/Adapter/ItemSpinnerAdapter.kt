package com.example.digitalact.Presentation.UI.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalact.Dependencies
import com.example.digitalact.Presentation.ViewModel.FullNameExecutorViewModel
import com.example.digitalact.Presentation.ViewModel.InstallationLocationViewModel
import com.example.digitalact.Presentation.ViewModel.TypeNewPUViewModel
import com.example.digitalact.R

class ItemSpinnerAdapter(val context: Context?,private val itemList: MutableList<String>) : RecyclerView.Adapter<ItemSpinnerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_spinner, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameItem.text = itemList[position]

        holder.deleteButton.setOnClickListener {
            val nameTable = context?.getSharedPreferences("tableName", Context.MODE_PRIVATE)?.getString("tableName", "").toString()
            when (nameTable) {
                "TypeNewPU" -> {
                    val typeNewPUViewModel by lazy { TypeNewPUViewModel(Dependencies.typeNewPURepository) }
                    typeNewPUViewModel.deleteSpinnerData(itemList[position])
                }
                "InstallationLocation" -> {
                    val installationLocationViewModel by lazy { InstallationLocationViewModel(Dependencies.installationLocationRepository) }
                    installationLocationViewModel.deleteSpinnerData(itemList[position])
                }
                "FullNameExecutor" -> {
                    val fullNameExecutorViewModel by lazy { FullNameExecutorViewModel(Dependencies.fullNameExecutorRepository) }
                    fullNameExecutorViewModel.deleteSpinnerData(itemList[position])
                }
            }
            deleteItem(position)

        }
    }

    override fun getItemCount() = itemList.size

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameItem: TextView = itemView.findViewById(R.id.nameItem)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    private fun deleteItem(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemList.size)
    }


}