package com.jakey.simplefeedingtracker.presentation.update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jakey.simplefeedingtracker.R
import com.jakey.simplefeedingtracker.data.model.Feeding
import com.jakey.simplefeedingtracker.data.viewmodel.FeedingsViewModel
import com.jakey.simplefeedingtracker.databinding.FragmentUpdateBinding
import com.jakey.simplefeedingtracker.utils.Helper
import java.util.*

class UpdateFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var viewModel: FeedingsViewModel

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateBinding.inflate(
            inflater,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(FeedingsViewModel::class.java)


        binding.etAmountUpdate.setText(args.currentUser.amount)
        binding.etDayUpdate.setText(args.currentUser.day)
        binding.etTimeUpdate.setText(args.currentUser.time)
        binding.etNoteUpdate.setText(args.currentUser.note)


        binding.etDayUpdate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                this,
                viewModel.cal.get(Calendar.YEAR),
                viewModel.cal.get(Calendar.MONTH),
                viewModel.cal.get(Calendar.DAY_OF_MONTH)
            ).show()

        }

        binding.etTimeUpdate.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                this,
                viewModel.cal.get(Calendar.HOUR_OF_DAY),
                viewModel.cal.get(Calendar.MINUTE),
                false
            ).show()
        }

        binding.buttonUpdate.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun updateItem() {
        val amount = binding.etAmountUpdate.text.toString()
        val time = binding.etTimeUpdate.text.toString()
        val day = binding.etDayUpdate.text.toString()
        val note = binding.etNoteUpdate.text.toString()
        val timeStamp = viewModel.cal.timeInMillis

        if (inputCheck(day, time, amount)) {
            val updatedFeeding = Feeding(
                args.currentUser.id,
                day = day,
                time = time,
                amount = amount,
                note = note,
                timeStamp = timeStamp
            )

            viewModel.updateFeeding(updatedFeeding)

            Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(day: String, time: String, amount: String): Boolean {
        return !(TextUtils.isEmpty(day) || TextUtils.isEmpty(time) || TextUtils.isEmpty(amount))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteFeeding()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteFeeding() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteFeeding(args.currentUser)
            Toast.makeText(requireContext(), "Successfully removed entry", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete this entry?")
        builder.setMessage("Are you sure you want to delete this entry?")
        builder.create().show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.cal.set(Calendar.YEAR, year)
        viewModel.cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        viewModel.cal.set(Calendar.MONTH, month)
        binding.etDayUpdate.setText(Helper.convertDay(viewModel.cal.timeInMillis))

        TimePickerDialog(
            requireContext(),
            this,
            viewModel.cal.get(Calendar.HOUR_OF_DAY),
            viewModel.cal.get(Calendar.MINUTE),
            false
        ).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        viewModel.cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
        viewModel.cal.set(Calendar.MINUTE, minute)

        binding.etTimeUpdate.setText(Helper.convertTime(viewModel.cal.timeInMillis))
    }
}