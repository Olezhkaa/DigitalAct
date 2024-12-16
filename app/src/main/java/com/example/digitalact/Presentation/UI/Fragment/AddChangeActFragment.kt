package com.example.digitalact.Presentation.UI.Fragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.NavHostFragment
import com.example.digitalact.Data.Repository.TelephoneRepositoryImpl
import com.example.digitalact.Dependencies
import com.example.digitalact.Domains.Model.Act
import com.example.digitalact.Domains.Model.Task
import com.example.digitalact.Presentation.UI.Activity.ScannerActivity
import com.example.digitalact.Presentation.ViewModel.ActViewModel
import com.example.digitalact.Presentation.ViewModel.FullNameExecutorViewModel
import com.example.digitalact.Presentation.ViewModel.InstallationLocationViewModel
import com.example.digitalact.Presentation.ViewModel.TaskViewModel
import com.example.digitalact.Presentation.ViewModel.TelephoneViewModel
import com.example.digitalact.Presentation.ViewModel.TypeNewPUViewModel
import com.example.digitalact.R
import com.example.digitalact.databinding.FragmentAddChangeActBinding
import java.util.Calendar

class AddChangeActFragment : Fragment() {

    private lateinit var binding: FragmentAddChangeActBinding

    private var page: Int = 1

    private lateinit var scanResultLauncher: ActivityResultLauncher<Intent>
    private var scanningForMeter = false

    private val SMS_PERMISSION_CODE = 101

    private val taskViewModel by lazy { TaskViewModel(Dependencies.taskRepository) }
    private val typeNewPUViewModel by lazy { TypeNewPUViewModel(Dependencies.typeNewPURepository) }
    private val installationLocationViewModel by lazy { InstallationLocationViewModel(Dependencies.installationLocationRepository) }
    private val fullNameExecutorViewModel by lazy { FullNameExecutorViewModel(Dependencies.fullNameExecutorRepository) }
    private val telephoneViewModel by lazy {TelephoneViewModel(Dependencies.telephoneRepository)}
    private val actViewModel by lazy { ActViewModel(Dependencies.actRepository) }

    private var actImport: Act? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddChangeActBinding.inflate(layoutInflater)
        val root = binding.root

        //Загрузка данных в Spinner
        uploadSpinner()
        //Загрузка даты
        setDateCompletion()
        //Загрузка полей на странице
        goPageFront(page)

        if(arguments?.getParcelable<Act>("act") != null) {
            actImport = arguments?.getParcelable("act")!!
        }

        if(actImport == null) {
            binding.deleteButton.visibility = GONE
        }
        else {
            binding.accountNumberSpinner.isEnabled = false
            taskViewModel.allTask.observe(viewLifecycleOwner) {list ->
                var task = Task()
                var flag = false
                for(i in list) {
                    if (actImport?.accountNumber == i.accountNumber) {
                        binding.accountNumberSpinner.setSelection(list.indexOf(i))
                        flag = true
                    }
                }
                if (!flag) {
                    task = Task(actImport!!.accountNumber, actImport!!.address, actImport!!.fullName, actImport!!.typeOldPU, actImport!!.numberOldPU,
                        actImport!!.reasonReplacement, actImport!!.numberApplication, "", actImport!!.comment)
                    taskViewModel.insertNewTaskDataInDatabase(actImport!!.accountNumber, actImport!!.address, actImport!!.fullName, actImport!!.typeOldPU, actImport!!.numberOldPU,
                        actImport!!.reasonReplacement, actImport!!.numberApplication, "", actImport!!.comment)
                    binding.accountNumberSpinner.setSelection(list.indexOf(task))
                }
            }
            binding.typeOldPUEditText.setText(actImport!!.typeOldPU)
            binding.numberOldPUEditText.setText(actImport!!.numberOldPU)
            binding.typeOldTTEditText.setText(actImport!!.typeOldTT)
            binding.numberOldTTOneEditText.setText(actImport!!.numberOldTT.split("\n").get(0))
            binding.numberOldTTTwoEditText.setText(actImport!!.numberOldTT.split("\n").get(1))
            binding.numberOldTTThreeEditText.setText(actImport!!.numberOldTT.split("\n").get(2))

            typeNewPUViewModel.dataList.observe(viewLifecycleOwner) {list ->
                if(list.indexOf(actImport!!.typeNewPU) == -1)
                    typeNewPUViewModel.insertSpinnerData(actImport!!.typeNewPU)
                binding.typeNewPUSpinner.setSelection(list.indexOf(actImport!!.typeNewPU))
            }
            binding.numberNewPUEditText.setText(actImport!!.numberNewPU)
            binding.typeNewTTEditText.setText(actImport!!.typeNewTT)
            binding.numberNewTTOneEditText.setText(actImport!!.numberOldTT.split("\n").get(0))
            binding.numberNewTTTwoEditText.setText(actImport!!.numberOldTT.split("\n").get(1))
            binding.numberNewTTThreeEditText.setText(actImport!!.numberOldTT.split("\n").get(2))

            binding.sealPUOneEditText.setText(actImport!!.sealPUOne)
            binding.sealPUTwoEditText.setText(actImport!!.sealPUTwo)
            binding.sealPUThreeOneEditText.setText(actImport!!.sealPUThree.split("\n").get(0))
            binding.sealPUThreeTwoEditText.setText(actImport!!.sealPUThree.split("\n").get(1))
            binding.sealPUThreeThreeEditText.setText(actImport!!.sealPUThree.split("\n").get(2))

            binding.simCardEditText.setText(actImport!!.simCard)
            binding.dateCompletionEditText.setText(actImport!!.dateCompletion)

            installationLocationViewModel.dataList.observe(viewLifecycleOwner) {list ->
                if(list.indexOf(actImport!!.installationLocation) == -1)
                    installationLocationViewModel.insertSpinnerData(actImport!!.installationLocation)
                binding.installationLocationSpinner.setSelection(list.indexOf(actImport!!.installationLocation))
            }

            fullNameExecutorViewModel.dataList.observe(viewLifecycleOwner) {list ->
                if(list.indexOf(actImport!!.fullNameExecutor) == -1)
                    fullNameExecutorViewModel.insertSpinnerData(actImport!!.fullNameExecutor)
                binding.fullNameExecutorSpinner.setSelection(list.indexOf(actImport!!.fullNameExecutor))
            }

            binding.commentEditText.setText(actImport!!.comment)
        }



        binding.nextPageButton.setOnClickListener {
            if (page == 1) page = 2
            else if (page == 2) page = 1
            binding.changeActScrollView.fullScroll(ScrollView.FOCUS_UP)
            goPageFront(page)
        }

        binding.accountNumberSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val taskViewModel by lazy { TaskViewModel(Dependencies.taskRepository) }
                taskViewModel.getTaskDataByAccountNumber(binding.accountNumberSpinner.selectedItem.toString())
                taskViewModel.currentTask.observe(viewLifecycleOwner) { task ->
                    binding.addressEditText.text = task.address
                    binding.fullNameEditText.text = task.fullName
                    if(actImport == null) {
                        binding.typeOldPUEditText.setText(task.typePU)
                        binding.numberOldPUEditText.setText(task.numberPU)
                    }
                    binding.reasonReplacementEditText.text = task.reasonReplacement
                    binding.numberApplicationEditText.text = task.numberApplication

                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.typeNewPUSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                changeSpinner(binding.typeNewPUSpinner, "TypeNewPU")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.fullNameExecutorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                changeSpinner(binding.fullNameExecutorSpinner, "FullNameExecutor")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.installationLocationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                changeSpinner(binding.installationLocationSpinner, "InstallationLocation")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        scanResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val scanResult = result.data?.getStringExtra("SCAN_RESULT")
                if(scanningForMeter) binding.numberNewPUEditText.text = scanResult
                else {
                    telephoneViewModel.getNumberTelephoneByICC(scanResult!!)
                    telephoneViewModel.getNumberTelephone.observe(viewLifecycleOwner) { numberTelephone ->
                        if(numberTelephone != null){
                            binding.simCardEditText.text = numberTelephone
                            binding.sendMessageButton.visibility = VISIBLE
                        }else Toast.makeText(context, "Данного номера нет в списке\nЗагрузите номера телефонов", Toast.LENGTH_LONG).show()
                    }

                }
            }
        }

        binding.barcodeNumberNewPUButton.setOnClickListener {
            scanningForMeter = true
            scanResultLauncher.launch(Intent(context, ScannerActivity::class.java))
        }

        binding.simCardButton.setOnClickListener {
            scanningForMeter = false
            scanResultLauncher.launch(Intent(context, ScannerActivity::class.java))
            //startBarcodeScanner()
        }

        binding.sendMessageButton.setOnClickListener(View.OnClickListener {
            if (binding.simCardEditText.text.trim().toString() != "") {
                checkAndRequestSmsPermission()
            }
            else Toast.makeText(context, "Отсканируйте SIM телефона", Toast.LENGTH_LONG).show()
        })

        binding.saveButton.setOnClickListener(View.OnClickListener {
            saveInDataBase()
        })

        binding.deleteButton.setOnClickListener(View.OnClickListener {
            if(actImport != null){
                actViewModel.deleteActDataByAccountNumber(actImport!!.accountNumber)

                Toast.makeText(context, "Данные успешно удалены", Toast.LENGTH_LONG).show()
                val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navFragment) as NavHostFragment
                val navController = navHostFragment.navController
                navController.navigate(R.id.actsFragment)
            }

        })

        return root
    }

    private fun checkAndRequestSmsPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.SEND_SMS), SMS_PERMISSION_CODE)
        } else {
            telephoneViewModel.sendMessageCode(binding.simCardEditText.text.trim().toString(), requireContext())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                telephoneViewModel.sendMessageCode(binding.simCardEditText.text.trim().toString(), requireContext())
            } else {
                Toast.makeText(context, "Необходимо разрешение для отправки SMS", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setDateCompletion() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        var month:String = (c.get(Calendar.MONTH)+1).toString()
        if(month.toInt() < 10) month = "0$month"
        var day:String = (c.get(Calendar.DAY_OF_MONTH)).toString()
        if(day.toInt() < 10) day = "0$day"
        binding.dateCompletionEditText.text = "$day.$month.$year"

        binding.dateCompletionButton.setOnClickListener{
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var month: String = monthOfYear.toString()
                month = (month.toInt()+1).toString()
                if (month.toInt()<10) month = "0$month"
                var day = dayOfMonth.toString()
                if (dayOfMonth<10) day = "0$day"
                binding.dateCompletionEditText.setText("$day.$month.$year")
            }, year, month.toInt()-1, day.toInt())
            dpd.show()
        }
    }

    private fun uploadSpinner() {

        val taskViewModel by lazy { TaskViewModel(Dependencies.taskRepository) }
        taskViewModel.getAllTaskDataFromDatabase()
        taskViewModel.allTask.observe(viewLifecycleOwner) { listTask ->
            var list: MutableList<String> = mutableListOf()
            if (listTask.isNotEmpty()){
                for (i in taskViewModel.allTask.value!!) list.add(i.accountNumber)
            }
            else list.add("Пусто")

            val adapterAccountNumber: ArrayAdapter<Any?> = ArrayAdapter<Any?>(requireContext(), R.layout.item_from_spinner, list.toList())
            adapterAccountNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.accountNumberSpinner.setAdapter(adapterAccountNumber)
        }

        val typeNewPUViewModel by lazy { TypeNewPUViewModel(Dependencies.typeNewPURepository) }
        typeNewPUViewModel.getAllData()
        typeNewPUViewModel.dataList.observe(viewLifecycleOwner) { listTask ->
            var list: MutableList<String> = mutableListOf()
            if (listTask.isNotEmpty()){
                list = listTask.toMutableList()
            }
            else list.add("Пусто")
            list.add("Изменить")

            val adapterNewTypePU: ArrayAdapter<Any?> = ArrayAdapter<Any?>(requireContext(), R.layout.item_from_spinner, list.toList())
            adapterNewTypePU.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.typeNewPUSpinner.setAdapter(adapterNewTypePU)
        }

        val installationLocationViewModel by lazy { InstallationLocationViewModel(Dependencies.installationLocationRepository) }
        installationLocationViewModel.getAllData()
        installationLocationViewModel.dataList.observe(viewLifecycleOwner) { listTask ->
            var list: MutableList<String> = mutableListOf()
            if (listTask.isNotEmpty()){
                list = listTask.toMutableList()
            }
            else list.add("Пусто")
            list.add("Изменить")

            val adapterFullNameExecutor: ArrayAdapter<Any?> = ArrayAdapter<Any?>(requireContext(), R.layout.item_from_spinner, list.toList())
            adapterFullNameExecutor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.installationLocationSpinner.setAdapter(adapterFullNameExecutor)
        }

        val fullNameExecutorViewModel by lazy { FullNameExecutorViewModel(Dependencies.fullNameExecutorRepository) }
        fullNameExecutorViewModel.getAllData()
        fullNameExecutorViewModel.dataList.observe(viewLifecycleOwner) { listTask ->
            var list: MutableList<String> = mutableListOf()
            if (listTask.isNotEmpty()){
                list = listTask.toMutableList()
            }
            else list.add("Пусто")
            list.add("Изменить")

            val adapterInstallationLocation: ArrayAdapter<Any?> = ArrayAdapter<Any?>(requireContext(), R.layout.item_from_spinner, list.toList())
            adapterInstallationLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.fullNameExecutorSpinner.setAdapter(adapterInstallationLocation)
        }
    }

    private fun changeSpinner(spinner: Spinner, tableName: String) {
        if (spinner.selectedItem.toString() == "Изменить") {
            val prefEditor: SharedPreferences.Editor = activity?.getSharedPreferences("tableName", Context.MODE_PRIVATE)!!.edit()
            prefEditor.putString("tableName", tableName)
            prefEditor.apply()
            val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navFragment) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.changeSpinnerFragment)
            spinner.setSelection(0)
        }
    }

    private fun goPageFront(page: Int) {
        if (page == 1) {
            binding.nextPageButton.text = "Следующая страница"
            binding.saveButton.visibility = GONE

            binding.deleteButton.visibility = GONE

            binding.oldFill.visibility = VISIBLE

            binding.lastFill.visibility = GONE

            binding.typeNewPUTextView.visibility = GONE
            binding.typeNewPURelativeLayout.visibility = GONE

            binding.numberNewPUTextView.visibility = GONE
            binding.numberNewPURelativeLayout.visibility = GONE

            binding.sealPUTextView.visibility = GONE
            binding.sealPUOneEditText.visibility = GONE

            binding.sealPUTwoEditText.visibility = GONE


            binding.typeNewTTTextView.visibility = GONE
            binding.typeNewTTEditText.visibility = GONE

            binding.numberNewTTOneTextView.visibility = GONE
            binding.numberNewTTOneEditText.visibility = GONE
            binding.numberNewTTTwoEditText.visibility = GONE
            binding.numberNewTTThreeEditText.visibility = GONE

            binding.sealPUThree.visibility = GONE

            binding.simCardTextView.visibility = GONE
            binding.simCardRelativeLayout.visibility = GONE
        }
        else if (page == 2) {
            binding.nextPageButton.text = "Предыдущая страница"
            binding.saveButton.visibility = VISIBLE

            if (actImport != null) {
                binding.deleteButton.visibility = VISIBLE
            }

            binding.oldFill.visibility = GONE

            binding.typeNewPUTextView.visibility = VISIBLE
            binding.typeNewPURelativeLayout.visibility = VISIBLE

            binding.numberNewPUTextView.visibility = VISIBLE
            binding.numberNewPURelativeLayout.visibility = VISIBLE

            binding.sealPUTextView.visibility = VISIBLE
            binding.sealPUOneEditText.visibility = VISIBLE

            binding.sealPUTwoEditText.visibility = VISIBLE


            binding.typeNewTTTextView.visibility = VISIBLE
            binding.typeNewTTEditText.visibility = VISIBLE

            binding.numberNewTTOneTextView.visibility = VISIBLE
            binding.numberNewTTOneEditText.visibility = VISIBLE
            binding.numberNewTTTwoEditText.visibility = VISIBLE
            binding.numberNewTTThreeEditText.visibility = VISIBLE

            binding.sealPUThree.visibility = VISIBLE


            binding.simCardTextView.visibility = VISIBLE
            binding.simCardRelativeLayout.visibility = VISIBLE

            binding.lastFill.visibility = VISIBLE
        }
    }

    private fun saveInDataBase() {
        if(binding.accountNumberSpinner.selectedItem.toString() != "Пусто"
            && !binding.addressEditText.text.toString().isEmpty()
            && !binding.reasonReplacementEditText.text.toString().isEmpty()
            && binding.typeNewPUSpinner.selectedItem.toString() != "Пусто"
            && !binding.numberNewPUEditText.text.toString().isEmpty()
            && !binding.dateCompletionEditText.text.toString().isEmpty()
            && binding.fullNameExecutorSpinner.selectedItem.toString() != "Пусто") {

            var locationInstantiation = ""
            if(!binding.installationLocationSpinner.selectedItem.toString().equals("Пусто")) locationInstantiation = binding.installationLocationSpinner.selectedItem.toString()

            val numberOldTT = binding.numberOldTTOneEditText.text.toString() + "\n" + binding.numberOldTTTwoEditText.text.toString() + "\n" + binding.numberOldTTThreeEditText.text.toString()
            val numberNewTT = binding.numberNewTTOneEditText.text.toString() + "\n" + binding.numberNewTTTwoEditText.text.toString() + "\n" + binding.numberNewTTThreeEditText.text.toString()
            val sealPUThreeOne = binding.sealPUThreeOneEditText.text.toString() + "\n" + binding.sealPUThreeTwoEditText.text.toString() + "\n" + binding.sealPUThreeThreeEditText.text.toString()

            if(actImport == null) {
                actViewModel.insertNewActDataInDatabase(
                    binding.accountNumberSpinner.selectedItem.toString(), binding.addressEditText.text.toString(), binding.fullNameEditText.text.toString(),
                    binding.typeOldPUEditText.text.toString(), binding.numberOldPUEditText.text.toString(), binding.typeOldTTEditText.text.toString(), numberOldTT, binding.reasonReplacementEditText.text.toString(), binding.numberApplicationEditText.text.toString(),
                    binding.typeNewPUSpinner.selectedItem.toString(), binding.numberNewPUEditText.text.toString(), binding.typeNewTTEditText.text.toString(), numberNewTT, binding.sealPUOneEditText.text.toString(), binding.sealPUTwoEditText.text.toString(),
                    sealPUThreeOne, binding.simCardEditText.text.toString(),
                    binding.dateCompletionEditText.text.toString(), binding.fullNameExecutorSpinner.selectedItem.toString(), locationInstantiation, binding.commentEditText.text.toString()
                )
            }
            else {
                actViewModel.updateActData(
                    binding.accountNumberSpinner.selectedItem.toString(), binding.addressEditText.text.toString(), binding.fullNameEditText.text.toString(),
                    binding.typeOldPUEditText.text.toString(), binding.numberOldPUEditText.text.toString(), binding.typeOldTTEditText.text.toString(), numberOldTT, binding.reasonReplacementEditText.text.toString(), binding.numberApplicationEditText.text.toString(),
                    binding.typeNewPUSpinner.selectedItem.toString(), binding.numberNewPUEditText.text.toString(), binding.typeNewTTEditText.text.toString(), numberNewTT, binding.sealPUOneEditText.text.toString(), binding.sealPUTwoEditText.text.toString(),
                    sealPUThreeOne, binding.simCardEditText.text.toString(),
                    binding.dateCompletionEditText.text.toString(), binding.fullNameExecutorSpinner.selectedItem.toString(), locationInstantiation, binding.commentEditText.text.toString()
                )
            }

            Toast.makeText(context, "Данные успешно записаны", Toast.LENGTH_LONG).show()
            val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navFragment) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.actsFragment)

        } else Toast.makeText(context, "Заполните все необходимые поля", Toast.LENGTH_LONG).show()

    }

}