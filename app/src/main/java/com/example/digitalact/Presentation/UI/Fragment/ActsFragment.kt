package com.example.digitalact.Presentation.UI.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalact.Dependencies
import com.example.digitalact.Domains.Model.Act
import com.example.digitalact.Presentation.UI.Adapter.ActClickListener
import com.example.digitalact.Presentation.UI.Adapter.ItemActAdapter
import com.example.digitalact.Presentation.ViewModel.ActViewModel
import com.example.digitalact.R
import com.example.digitalact.databinding.FragmentActsBinding

class ActsFragment : Fragment(), ActClickListener {

    private lateinit var binding: FragmentActsBinding

    private val actViewModel by lazy { ActViewModel(Dependencies.actRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActsBinding.inflate(layoutInflater)
        val root = binding.root

        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navFragment) as NavHostFragment
        val navController = navHostFragment.navController

        actViewModel.allAct.observe(viewLifecycleOwner) { actList ->
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.setLayoutManager(LinearLayoutManager(context))
            binding.recyclerView.adapter = ItemActAdapter(context , actList.toMutableList(), this)

            onEmptyRecycleView(actList)
        }



        binding.AddButton.setOnClickListener(View.OnClickListener {
            navController.navigate(R.id.choiceActFragment)
        })

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

    override fun onActClicked(act: Act?) {
        val bundle = Bundle().apply {
            putParcelable("act", act) // Передача объекта Replacements в виде Parcelable
        }

        // Замена текущего фрагмента на новый
        val host: NavHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navFragment) as NavHostFragment
        val navController = host.navController

        navController.navigate(R.id.addChangeActFragment, bundle)
    }

}