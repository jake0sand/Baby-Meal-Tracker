package com.jakey.simplefeedingtracker.presentation.list

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakey.simplefeedingtracker.R
import com.jakey.simplefeedingtracker.data.model.DataPoint
import com.jakey.simplefeedingtracker.databinding.FragmentListBinding
import com.jakey.simplefeedingtracker.data.viewmodel.FeedingsViewModel


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var feedingsViewModel: FeedingsViewModel

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
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        

        feedingsViewModel = ViewModelProvider(this).get(FeedingsViewModel::class.java)
        feedingsViewModel.readAllFeedings.observe(viewLifecycleOwner) {
            adapter.setData(it)
        //Setting since last time
        // binding.amount.text =  System.currentTimeMillis() - it[0].timestamp
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }



        setHasOptionsMenu(true)



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