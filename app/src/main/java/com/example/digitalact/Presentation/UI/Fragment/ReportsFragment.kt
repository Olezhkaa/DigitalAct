package com.example.digitalact.Presentation.UI.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalact.Presentation.UI.Adapter.ItemSpinnerAdapter
import com.example.digitalact.R
import com.example.digitalact.databinding.FragmentReportsBinding

class ReportsFragment : Fragment() {

    private lateinit var binding: FragmentReportsBinding

    private lateinit var reportsList: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportsBinding.inflate(layoutInflater)
        val root = binding.root

        reportsList = mutableListOf()

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.setLayoutManager(LinearLayoutManager(context))
        binding.recyclerView.adapter = ItemSpinnerAdapter(context, reportsList)

        onEmptyRecycleView(reportsList)

        return root
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