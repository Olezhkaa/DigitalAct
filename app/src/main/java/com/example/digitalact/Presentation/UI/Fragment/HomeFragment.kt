package com.example.digitalact.Presentation.UI.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.digitalact.Data.Repository.ActRepositoryImpl
import com.example.digitalact.Dependencies
import com.example.digitalact.Presentation.ViewModel.ActViewModel
import com.example.digitalact.Presentation.ViewModel.TaskViewModel
import com.example.digitalact.Presentation.ViewModel.TelephoneViewModel
import com.example.digitalact.R
import com.example.digitalact.databinding.FragmentHomeBinding
import java.util.Calendar

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val taskViewModel by lazy { TaskViewModel(Dependencies.taskRepository) }
    private val actViewModel by lazy { ActViewModel(Dependencies.actRepository) }
    private val telephoneViewModel by lazy { TelephoneViewModel(Dependencies.telephoneRepository) }

    private val fileName = "ReportDigitalAct ${dateTime()}"

    private val IMPORT_EXCEL_REQUEST = 1

    private var importDataName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val root = binding.root



        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navFragment) as NavHostFragment
        val navController = navHostFragment.navController


        binding.downloadTaskButton.setOnClickListener(View.OnClickListener {
            importDataName = "Task"
            openFilePicker()
        })

        binding.downloadTelephoneButton.setOnClickListener(View.OnClickListener {
            importDataName = "Telephone"
            openFilePicker()
        })

        binding.actsButton.setOnClickListener(View.OnClickListener{
            navController.navigate(R.id.actsFragment)
        })
        binding.routsButton.setOnClickListener(View.OnClickListener {
            navController.navigate(R.id.routsFragment)
        })
        binding.reportsButton.setOnClickListener(View.OnClickListener {
            //navController.navigate(R.id.reportsFragment)
            actViewModel.allAct.observe(viewLifecycleOwner) { actList ->
                if (actList.isNotEmpty()) {
                    actViewModel.saveActData(actList, fileName, context)
                    actViewModel.shareActData(fileName, context)
                    actViewModel.deleteAllActData()
                } else Toast.makeText(context, "В отчете нет данных", Toast.LENGTH_LONG).show()
            }

        })

        return root
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        startActivityForResult(intent, IMPORT_EXCEL_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode.equals(IMPORT_EXCEL_REQUEST) && resultCode.equals(Activity.RESULT_OK)) {
            if(importDataName == "Task"){
                taskViewModel.deleteAllTaskData()
                data?.data?.let { uri ->
                    taskViewModel.loadTaskData(uri, context)
                }
            }
            else if (importDataName == "Telephone") {
                telephoneViewModel.deleteAllTelephoneData()
                data?.data?.let {uri ->
                    telephoneViewModel.loadTelephoneData(uri, context)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    private fun dateTime() : String {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        var month:String = (c.get(Calendar.MONTH)+1).toString()
        if(month.toInt() < 10) month = "0$month"
        var day:String = (c.get(Calendar.DAY_OF_MONTH)).toString()
        if(day.toInt() < 10) day = "0$day"
        var hour:String = c.get(Calendar.HOUR_OF_DAY).toString()
        if(hour.toInt() < 10) hour = "0$hour"
        var min:String = c.get(Calendar.MINUTE).toString()
        if(min.toInt() < 10) min = "0$min"
        var sec:String = c.get(Calendar.SECOND).toString()
        if(sec.toInt() < 10) sec = "0$sec"
        return "$day-$month-$year $hour-$min-$sec"
    }
}