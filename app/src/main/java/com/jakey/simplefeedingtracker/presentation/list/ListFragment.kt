package com.jakey.simplefeedingtracker.presentation.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.jakey.simplefeedingtracker.R
import com.jakey.simplefeedingtracker.data.model.DataPoint
import com.jakey.simplefeedingtracker.data.viewmodel.FeedingsViewModel
import com.jakey.simplefeedingtracker.databinding.FragmentListBinding
import com.jakey.simplefeedingtracker.databinding.StickyTopHeaderBinding
import com.jakey.simplefeedingtracker.presentation.FeedingsListAdapter


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FeedingsViewModel

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

        val adapter = FeedingsListAdapter(data = data)
        val recyclerView = binding.rvFeeding
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)



        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapter.getItemViewType(position)) {
                        R.layout.feeding_item -> 1
                        R.layout.header_view_holder -> 2
                        else -> 2
                    }
                }
            }


        viewModel = ViewModelProvider(this).get(FeedingsViewModel::class.java)


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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_options) {

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}