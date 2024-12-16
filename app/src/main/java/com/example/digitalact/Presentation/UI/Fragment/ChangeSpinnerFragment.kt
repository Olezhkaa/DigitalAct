package com.example.digitalact.Presentation.UI.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalact.Dependencies
import com.example.digitalact.Presentation.UI.Adapter.ItemSpinnerAdapter
import com.example.digitalact.Presentation.ViewModel.FullNameExecutorViewModel
import com.example.digitalact.Presentation.ViewModel.InstallationLocationViewModel
import com.example.digitalact.Presentation.ViewModel.SpinnerViewModel
import com.example.digitalact.Presentation.ViewModel.TypeNewPUViewModel
import com.example.digitalact.R
import com.example.digitalact.databinding.FragmentChangeSpinnerBinding

class ChangeSpinnerFragment : Fragment() {

    private lateinit var binding: FragmentChangeSpinnerBinding

    private lateinit var listSpinner: MutableList<String>

    private lateinit var nameTable: String

    private val typeNewPUViewModel by lazy { TypeNewPUViewModel(Dependencies.typeNewPURepository) }
    private val installationLocationViewModel by lazy { InstallationLocationViewModel(Dependencies.installationLocationRepository) }
    private val fullNameExecutorViewModel by lazy { FullNameExecutorViewModel(Dependencies.fullNameExecutorRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeSpinnerBinding.inflate(layoutInflater)
        val root = binding.root

        nameTable = activity?.getSharedPreferences("tableName", Context.MODE_PRIVATE)?.getString("tableName", "").toString()

        when (nameTable) {
            "TypeNewPU" -> {
                typeNewPUViewModel.dataList.observe(viewLifecycleOwner) {list ->
                    setRecyclerView(list)
                }
            }
            "InstallationLocation" -> {
                installationLocationViewModel.dataList.observe(viewLifecycleOwner) {list ->
                    setRecyclerView(list)
                }
            }
            "FullNameExecutor" -> {
                fullNameExecutorViewModel.dataList.observe(viewLifecycleOwner) {list ->
                    setRecyclerView(list)
                }
            }
        }

        binding.addButton.setOnClickListener(View.OnClickListener {
            if(binding.addEditText.text.toString().trim().isNotEmpty()) {
                if (nameTable == "TypeNewPU") {
                    typeNewPUViewModel.insertSpinnerData(binding.addEditText.text.toString())
                    typeNewPUViewModel.dataList.observe(viewLifecycleOwner) {list ->
                        binding.recyclerView.adapter = ItemSpinnerAdapter(context, list.toMutableList())
                    }
                    typeNewPUViewModel.getAllData()
                    binding.addEditText.setText("")
                    return@OnClickListener
                }
                if (nameTable == "InstallationLocation") {
                    installationLocationViewModel.insertSpinnerData(binding.addEditText.text.toString())
                    installationLocationViewModel.dataList.observe(viewLifecycleOwner) {list ->
                        binding.recyclerView.adapter = ItemSpinnerAdapter(context, list.toMutableList())
                    }
                    installationLocationViewModel.getAllData()
                    binding.addEditText.setText("")
                    return@OnClickListener
                }
                if (nameTable == "FullNameExecutor") {
                    fullNameExecutorViewModel.insertSpinnerData(binding.addEditText.text.toString())
                    fullNameExecutorViewModel.dataList.observe(viewLifecycleOwner) {list ->
                        binding.recyclerView.adapter = ItemSpinnerAdapter(context, list.toMutableList())
                    }
                    fullNameExecutorViewModel.getAllData()
                    binding.addEditText.setText("")
                    return@OnClickListener

                }
            }
            else Toast.makeText(context, "Введите значение", Toast.LENGTH_LONG).show()
        })

        binding.backButton.setOnClickListener(View.OnClickListener {
            activity?.onBackPressed()
        })

        return root
    }

    private fun setRecyclerView(list: List<String>){
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.setLayoutManager(LinearLayoutManager(context))
        binding.recyclerView.adapter = ItemSpinnerAdapter(context, list.toMutableList())

        onEmptyRecycleView(list)
    }

    private fun onEmptyRecycleView(itemList:List<*>) {
        if(itemList.isEmpty()) {
            binding.emptyImageView.visibility = VISIBLE
            binding.emptyTextView.visibility = VISIBLE
        }
        else {
            binding.emptyImageView.visibility = GONE
            binding.emptyTextView.visibility = GONE
        }
    }

}