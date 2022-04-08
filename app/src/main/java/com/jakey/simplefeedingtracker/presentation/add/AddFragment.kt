package com.jakey.simplefeedingtracker.presentation.add

//import com.jakey.simplefeedingtracker.presentation.dialogs.TimePickerFragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jakey.simplefeedingtracker.R
import com.jakey.simplefeedingtracker.data.DataStoreManager
import com.jakey.simplefeedingtracker.data.model.Feeding
import com.jakey.simplefeedingtracker.presentation.SharedViewModel
import com.jakey.simplefeedingtracker.databinding.FragmentAddBinding
import com.jakey.simplefeedingtracker.utils.Helper
import kotlinx.coroutines.launch
import java.util.*
import java.util.jar.Manifest


// date time picker for setting time or day
class AddFragment : Fragment(), TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    //    private var date: Long = 0
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    lateinit var dataStoreManager: DataStoreManager

    private lateinit var viewModel: SharedViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentAddBinding.inflate(
            inflater,
            container,
            false
        )

        val currentTimeMillis = System.currentTimeMillis()



        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        viewModel.day = binding.etDay.setText(Helper.convertDay(currentTimeMillis)).toString()
        viewModel.time = binding.etTime.setText(Helper.convertTime(currentTimeMillis)).toString()

        dataStoreManager = DataStoreManager(requireContext())



        binding.etDay.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                this,
                viewModel.cal.get(Calendar.YEAR),
                viewModel.cal.get(Calendar.MONTH),
                viewModel.cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.etTime.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                this,
                viewModel.cal.get(Calendar.HOUR_OF_DAY),
                viewModel.cal.get(Calendar.MINUTE),
                false
            ).show()
        }



        binding.addSaveButton.setOnClickListener {

            insertFeeding()

        }


        binding.checkbox.setOnClickListener {

            lifecycleScope.launch {
                Toast.makeText(
                    requireContext(),
                    "Send feeding to ${
                        dataStoreManager.readPhoneNumber()
                    }",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        //Setting since last time
        // binding.amount.text =  System.currentTimeMillis() - it[0].timestamp


        return binding.root
    }


    private fun insertFeeding() {
        viewModel.day = binding.etDay.text.toString()
        viewModel.time = binding.etTime.text.toString()
        viewModel.amount = binding.etAmount.text.toString()
        viewModel.note = binding.etNote.text.toString()


        if (inputCheck(
                viewModel.day,
                viewModel.time,
                viewModel.amount,
                timestamp = viewModel.cal.timeInMillis
            )
        ) {
            val feeding = Feeding(
                day = viewModel.day,
                time = viewModel.time,
                amount = viewModel.amount,
                note = viewModel.note,
                timeStamp = viewModel.cal.timeInMillis
            )

            if (binding.checkbox.isChecked) {
                if (checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.SEND_SMS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.SEND_SMS),
                        101
                    )
                    val smsIntent = Intent(Intent.ACTION_VIEW)
                    lifecycleScope.launch {
                        val smileyFace = "\uD83D\uDE0A"
                        smsIntent.putExtra(
                            "sms_body",
                            "Just fed ${dataStoreManager.readBabyName()} $smileyFace\n" +
                                    "${viewModel.day}\n" +
                                    "${viewModel.time}\n" +
                                    "${viewModel.amount}oz" +
                                    "${viewModel.note}"
                        )
                        smsIntent.data =
                            Uri.parse("sms:${dataStoreManager.readPhoneNumber()}")

                    }
                    val builder =
                        AlertDialog.Builder(requireContext(), R.style.ThemeOverlay_Material3_Dialog)
                    builder.setPositiveButton("Yes") { _, _ ->
                        startActivity(smsIntent)

                    }
                    builder.setNegativeButton("No") { _, _ -> }
                    lifecycleScope.launch { builder.setMessage("Send message to ${dataStoreManager.readPhoneNumber()}") }
                    builder.create().show()

                }
            }

            viewModel.addFeeding(feeding)


            Toast.makeText(requireContext(), "Successfully Added Feeding", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(
                requireContext(),
                """Please fill all fields with *
                    |Check time not in the future.""".trimMargin(),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun inputCheck(
        day: String,
        time: String,
        amount: String,
        timestamp: Long
    ): Boolean {
        return !(TextUtils.isEmpty(day) || TextUtils.isEmpty(time) || TextUtils.isEmpty(amount) || timestamp > System.currentTimeMillis())
    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        viewModel.cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
        viewModel.cal.set(Calendar.MINUTE, minute)

        binding.etTime.setText(Helper.convertTime(viewModel.cal.timeInMillis))
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.cal.set(Calendar.YEAR, year)
        viewModel.cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        viewModel.cal.set(Calendar.MONTH, month)
        binding.etDay.setText(Helper.convertDay(viewModel.cal.timeInMillis))

        TimePickerDialog(
            requireContext(),
            this,
            viewModel.cal.get(Calendar.HOUR_OF_DAY),
            viewModel.cal.get(Calendar.MINUTE),
            false
        ).show()


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}