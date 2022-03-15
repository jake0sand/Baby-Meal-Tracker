package com.jakey.simplefeedingtracker.presentation.add

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jakey.simplefeedingtracker.R
import com.jakey.simplefeedingtracker.data.model.Feeding
import com.jakey.simplefeedingtracker.databinding.FragmentAddBinding
import com.jakey.simplefeedingtracker.data.viewmodel.FeedingsViewModel
import kotlinx.coroutines.coroutineScope
import java.text.SimpleDateFormat
import java.util.*

//TODO date time picker for setting time or day
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendar: Calendar
    private lateinit var sdfDay: SimpleDateFormat
    private lateinit var sdfTime: SimpleDateFormat



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

        calendar = Calendar.getInstance()
        sdfDay = SimpleDateFormat("EEEE")
        sdfTime = SimpleDateFormat("h:mm a")

        mViewModel = ViewModelProvider(this).get(FeedingsViewModel::class.java)
        binding.etDay.setText(sdfDay.format(calendar.time))
        binding.etTime.setText(sdfTime.format(calendar.time))
        binding.button.setOnClickListener {
            insertFeeding()

        }



        return binding.root
    }

    private fun insertFeeding() {
        val day = binding.etDay.text.toString()
        val time = binding.etTime.text.toString()
        val amount = binding.etAmount.text.toString()
        val note = binding.etNote.text.toString()

        if (inputCheck(day, time, amount)) {
            val feeding = Feeding(day = day, time = time, amount = amount, note = note)

            mViewModel.addFeeding(feeding)

            //TODO("Ask Dom how I can send an SMS text message alone with adding to DB")

            Toast.makeText(requireContext(), "Successfully Added Feeding", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(day: String, time: String, amount: String): Boolean {
        return !(TextUtils.isEmpty(day) || TextUtils.isEmpty(time) || TextUtils.isEmpty(amount))
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}