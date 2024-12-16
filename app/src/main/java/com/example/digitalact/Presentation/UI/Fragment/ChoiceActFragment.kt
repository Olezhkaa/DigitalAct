package com.example.digitalact.Presentation.UI.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.digitalact.R
import com.example.digitalact.databinding.FragmentChoiceActBinding

class ChoiceActFragment : Fragment() {

    private lateinit var binding: FragmentChoiceActBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChoiceActBinding.inflate(layoutInflater)
        val root = binding.root

        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.singlePhasePUButton.setOnClickListener(View.OnClickListener {
            navController.navigate(R.id.addChangeActFragment)

        })

        return root
    }

}