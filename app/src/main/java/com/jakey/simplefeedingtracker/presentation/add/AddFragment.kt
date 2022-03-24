package com.jakey.simplefeedingtracker.presentation.add

//import com.jakey.simplefeedingtracker.presentation.dialogs.TimePickerFragment
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jakey.simplefeedingtracker.R
import com.jakey.simplefeedingtracker.data.model.Feeding
import com.jakey.simplefeedingtracker.data.viewmodel.FeedingsViewModel
import com.jakey.simplefeedingtracker.databinding.FragmentAddBinding
import com.jakey.simplefeedingtracker.utils.Helper
import java.util.*

// date time picker for setting time or day
class AddFragment : Fragment(), TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    //    private var date: Long = 0
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!


    private lateinit var mViewModel: FeedingsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //this could be a situation where an inherited fragment would be clean


        _binding = FragmentAddBinding.inflate(
            inflater,
            container,
            false
        )
        /*
        Ask Dom about how to get Day of week and current time as default entries in EditText
        sdf = SimpleDateFormat("E").toString()
        calendar = Calendar.getInstance()
        val formatted = sdf.format(calendar.time)
        */

//        calendar = Calendar.getInstance()
//        sdfDay = SimpleDateFormat("EEEE")
//        sdfTime = SimpleDateFormat("h:mm a")
        val currentTimeMillis = System.currentTimeMillis()



        mViewModel = ViewModelProvider(this).get(FeedingsViewModel::class.java)

//        val datePickerFragment = DatePickerFragment()
//        val timePickerFragment = TimePickerFragment(date)
        val supportFragmentManager = requireActivity().supportFragmentManager


        binding.etDay.setText(Helper.convertDay(currentTimeMillis))
        binding.etTime.setText(Helper.convertTime(currentTimeMillis))

        binding.etDay.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                this,
                mViewModel.cal.get(Calendar.YEAR),
                mViewModel.cal.get(Calendar.MONTH),
                mViewModel.cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.addSaveButton.setOnClickListener {

            insertFeeding()

        }

        binding.etTime.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                this,
                mViewModel.cal.get(Calendar.HOUR_OF_DAY),
                mViewModel.cal.get(Calendar.MINUTE),
                false
            ).show()
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun insertFeeding() {
        val day = binding.etDay.text.toString()
        val time = binding.etTime.text.toString()
        val amount = binding.etAmount.text.toString()
        val note = binding.etNote.text.toString()


        if (inputCheck(day, time, amount)) {
            val feeding = Feeding(
                day = day,
                time = time,
                amount = amount,
                note = note,
                timeStamp = mViewModel.cal.timeInMillis
            )

            mViewModel.addFeeding(feeding)


            Toast.makeText(requireContext(), "Successfully Added Feeding", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(day: String, time: String, amount: String): Boolean {
        return !(TextUtils.isEmpty(day) || TextUtils.isEmpty(time) || TextUtils.isEmpty(amount))
    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        mViewModel.cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
        mViewModel.cal.set(Calendar.MINUTE, minute)

        binding.etTime.setText(Helper.convertTime(mViewModel.cal.timeInMillis))
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mViewModel.cal.set(Calendar.YEAR, year)
        mViewModel.cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        mViewModel.cal.set(Calendar.MONTH, month)
        binding.etDay.setText(Helper.convertDay(mViewModel.cal.timeInMillis))

        TimePickerDialog(
            requireContext(),
            this,
            mViewModel.cal.get(Calendar.HOUR_OF_DAY),
            mViewModel.cal.get(Calendar.MINUTE),
            false
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}