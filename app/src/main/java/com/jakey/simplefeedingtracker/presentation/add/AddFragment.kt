package com.jakey.simplefeedingtracker.presentation.add

//import com.jakey.simplefeedingtracker.presentation.dialogs.TimePickerFragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
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
                //TODO change this note setText(). Implement sms Intent with "dataStoreManager.readPhoneNumber()"
                binding.etNote.setText(dataStoreManager.readPhoneNumber().toString())
            }
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

            viewModel.addFeeding(feeding)


            Toast.makeText(requireContext(), "Successfully Added Feeding", Toast.LENGTH_LONG)
                .show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(
                requireContext(),
                """Please fill all fields with *
                    |Check time not in the future.""".trimMargin(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun inputCheck(day: String, time: String, amount: String, timestamp: Long): Boolean {
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


//
//    fun smsSendMessage(view: View?) {
//        val textView = findViewById(R.id.number_to_call) as TextView
//        // Use format with "smsto:" and phone number to create smsNumber.
//        val smsNumber = String.format(
//            "smsto: %s",
//            textView.text.toString()
//        )
//        // Find the sms_message view.
//        val smsEditText = findViewById(R.id.sms_message) as EditText
//        // Get the text of the sms message.
//        val sms = smsEditText.text.toString()
//        // Create the intent.
//        val smsIntent = Intent(Intent.ACTION_SENDTO)
//        // Set the data for the intent as the phone number.
//        smsIntent.data = Uri.parse(smsNumber)
//        // Add the message (sms) with the key ("sms_body").
//        smsIntent.putExtra("sms_body", sms)
//        // If package resolves (target app installed), send intent.
//        if (smsIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(smsIntent)
//        } else {
//            Log.d(TAG, "Can't resolve app for ACTION_SENDTO Intent")
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}