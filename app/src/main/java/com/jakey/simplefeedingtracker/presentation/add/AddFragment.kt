package com.jakey.simplefeedingtracker.presentation.add

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jakey.simplefeedingtracker.data.Feeding
import com.jakey.simplefeedingtracker.databinding.FragmentAddBinding
import com.jakey.simplefeedingtracker.presentation.FeedingsViewModel


class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mViewModel: FeedingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(
            inflater,
            container,
            false
        )

        mViewModel = ViewModelProvider(this).get(FeedingsViewModel::class.java)

        binding.button.setOnClickListener {
            insertFeeding()

        }

        return binding.root
    }

    private fun insertFeeding() {
        val day = binding.etDay.text.toString()
        val time = binding.etTime.text.toString()
        val amount = binding.etAmount.text.toString()

        if (inputCheck(day, time, amount)) {
            val feeding = Feeding(day = day, time = time, amount = amount)

            mViewModel.addFeeding(feeding)
        }
    }

    private fun inputCheck(day: String, time: String, amount: String): Boolean {
        return !(TextUtils.isEmpty(day) && TextUtils.isEmpty(time) && TextUtils.isEmpty(amount))
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}