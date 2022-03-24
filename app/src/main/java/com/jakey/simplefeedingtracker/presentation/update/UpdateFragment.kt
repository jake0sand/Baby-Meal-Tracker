package com.jakey.simplefeedingtracker.presentation.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
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

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var feedingsViewModel: FeedingsViewModel

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //this could be a situation where an inherited fragment would be clean


        _binding = FragmentUpdateBinding.inflate(
            inflater,
            container,
            false
        )
//        val datePickerFragment = DatePickerFragment()
        val supportFragmentManager = requireActivity().supportFragmentManager
        feedingsViewModel = ViewModelProvider(this).get(FeedingsViewModel::class.java)

        binding.etAmountUpdate.setText(args.currentUser.amount)
        binding.etDayUpdate.setText(args.currentUser.day)
        binding.etTimeUpdate.setText(args.currentUser.time)
        binding.etNoteUpdate.setText(args.currentUser.note)

        val currentTimeMillis = System.currentTimeMillis()


        
        binding.etDayUpdate.setOnClickListener {

            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getString("SELECTED_DATE")

                    binding.etDayUpdate.setText(date)
                    binding.etTimeUpdate.setText(Helper.convertTime(currentTimeMillis))
                }
            }

//            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }

        binding.etTimeUpdate.setOnClickListener {

            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getString("SELECTED_DATE")

                    binding.etDayUpdate.setText(date)
                    binding.etTimeUpdate.setText(Helper.convertTime(currentTimeMillis))
                }
            }

//            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
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

        if (inputCheck(day, time, amount)) {
            val updatedFeeding = Feeding(
                args.currentUser.id,
                day,
                time = time,
                amount = amount,
                note = note
            )

            feedingsViewModel.updateFeeding(updatedFeeding)

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
            feedingsViewModel.deleteFeeding(args.currentUser)
            Toast.makeText(requireContext(), "Successfully removed entry", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete this entry?")
        builder.setMessage("Are you sure you want to delete this entry?")
        builder.create().show()
    }
}