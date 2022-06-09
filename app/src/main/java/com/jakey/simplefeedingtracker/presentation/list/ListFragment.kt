package com.jakey.simplefeedingtracker.presentation.list

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jakey.simplefeedingtracker.R
import com.jakey.simplefeedingtracker.data.DataStoreManager
import com.jakey.simplefeedingtracker.data.model.DataPoint
import com.jakey.simplefeedingtracker.data.model.Feeding
import com.jakey.simplefeedingtracker.presentation.SharedViewModel
import com.jakey.simplefeedingtracker.databinding.FragmentListBinding
import com.jakey.simplefeedingtracker.presentation.FeedingsListAdapter
import kotlinx.coroutines.launch


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    lateinit var dataStoreManager: DataStoreManager
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val data = mutableListOf<DataPoint>()
        //this could be a situation where an inherited fragment would be clean

        _binding = FragmentListBinding.inflate(
            inflater,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        val adapter = FeedingsListAdapter(data = data, onDeleteClick = {
            deleteFeeding(it)
        }

        )
        val recyclerView = binding.rvFeeding
        recyclerView.adapter = adapter
//        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
//        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup =
//            object : GridLayoutManager.SpanSizeLookup() {
//                override fun getSpanSize(position: Int): Int {
//                    return when (adapter.getItemViewType(position)) {
//                        R.layout.feeding_item -> 1
//                        R.layout.header_view_holder -> 2
//                        else -> 2
//                    }
//                }
//            }

        dataStoreManager = DataStoreManager(requireContext())

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)
        viewModel.readAllFeedings.observe(viewLifecycleOwner) {
            adapter.setData(it)
            //Setting since last time
            // binding.amount.text =  System.currentTimeMillis() - it[0].timestamp
        }



        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
    }


//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.menu_options) {
//
//        }
//        return super.onOptionsItemSelected(item)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.set_phone_number -> {
                setPhoneNumberDialog()
                true
            }

            R.id.delete_all_entries -> {
                val builder = AlertDialog.Builder(requireContext(), R.style.ThemeOverlay_Material3_Dialog)
                builder.setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteAllFeedings()
                    Toast.makeText(requireContext(), "Successfully removed all entries", Toast.LENGTH_SHORT)
                        .show()
                }
                builder.setNegativeButton("No") { _, _ -> }
                builder.setMessage("Are you sure you want to delete ALL entries?")
                builder.create().show()
                true
            }

            R.id.set_baby_name -> {
                babyNameDialog()
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun setPhoneNumberDialog() {
        val dialogBuilder =
            AlertDialog.Builder(requireContext(), R.style.ThemeOverlay_Material3_Dialog)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.edit_text_custom_dialog, null)
        dialogBuilder.setView(dialogView)
        val editText: EditText = dialogView.findViewById(R.id.edit_text_1)

        dialogBuilder.apply {
            setTitle("Set Partner's phone #")
            lifecycleScope.launch { editText.setText(dataStoreManager.readPhoneNumber()) }
            setPositiveButton("Save") { _, _ ->
                lifecycleScope.launch {
                    binding
                    dataStoreManager.savePhoneNumber(value = editText.text.toString())

                    Toast.makeText(
                        requireContext(),
                        "${dataStoreManager.readPhoneNumber()} saved as partner's #",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

            }
            setNegativeButton(
                "Cancel"
            ) { _, _ ->

            }.show()
        }
    }

    private fun babyNameDialog() {
        val dialogBuilder =
            AlertDialog.Builder(requireContext(), R.style.ThemeOverlay_Material3_Dialog)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.edit_text_custom_dialog, null)
        dialogBuilder.setView(dialogView)
        val editText: EditText = dialogView.findViewById(R.id.edit_text_1)
        editText.inputType = InputType.TYPE_CLASS_TEXT

        dialogBuilder.apply {
            setTitle("Set Baby's name")
            lifecycleScope.launch { editText.setText(dataStoreManager.readBabyName()) }
            setPositiveButton("Save") { _, _ ->
                lifecycleScope.launch {
                    binding
                    dataStoreManager.saveBabyName(value = editText.text.toString())

                    Toast.makeText(
                        requireContext(),
                        "${dataStoreManager.readBabyName()} saved as Baby's name",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

            }
            setNegativeButton(
                "Cancel"
            ) { _, _ ->

            }.show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

//    private fun deleteFeeding() {
//
//
//        Toast.makeText(requireContext(), "Successfully removed entry", Toast.LENGTH_SHORT)
//            .show()
//    }
    private fun deleteFeeding(feeding: Feeding) {

        val builder = AlertDialog.Builder(requireContext(), R.style.ThemeOverlay_Material3_Dialog)
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteFeeding(feeding)
            Toast.makeText(requireContext(), "Successfully removed entry", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete this entry?")
        builder.setMessage("Are you sure you want to delete this entry?")
        builder.create().show()
    }
}

