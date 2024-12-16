package com.example.digitalact.Presentation.UI.Fragment

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
import com.example.digitalact.Domains.Model.Task
import com.example.digitalact.Presentation.UI.Adapter.ItemRouteAdapter
import com.example.digitalact.Presentation.UI.Adapter.ItemSpinnerAdapter
import com.example.digitalact.Presentation.ViewModel.TaskViewModel
import com.example.digitalact.R
import com.example.digitalact.databinding.FragmentRoutsBinding

class RoutsFragment : Fragment() {

    private lateinit var binding: FragmentRoutsBinding

    private lateinit var routsList: MutableList<Task>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoutsBinding.inflate(layoutInflater)
        val root = binding.root

        /*val viewModel by lazy {TaskViewModel(Dependencies.taskRepository)}
        viewModel.insertNewTaskDataInDatabase("11111", "1111", "111", "111", "1", "1", "1", "1", "11")
        viewModel.allTask.observe(viewLifecycleOwner) { tasks ->
            Toast.makeText(context, tasks.size.toString(), Toast.LENGTH_LONG).show()
        }*/


        routsList = mutableListOf(Task("777770001", "г.Валуйки, ул.Фрунзе, д.71", "Фадеев Олег Григорьевич", "1 – фазный PLC", "111111", "Поломка", "1", "+79511338535", "Приходить с 8:00 до 17:00"))

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.setLayoutManager(LinearLayoutManager(context))
        binding.recyclerView.adapter = ItemRouteAdapter(context, routsList)

        onEmptyRecycleView(routsList)

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