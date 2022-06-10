package com.jakey.simplefeedingtracker.presentation.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.jakey.simplefeedingtracker.R
import com.jakey.simplefeedingtracker.databinding.FragmentFirstScreenBinding


class FirstScreen : Fragment() {

    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        binding.imageClickAndHold.setOnClickListener {
            binding.tvClickAndHold.visibility = View.VISIBLE
        }

        binding.imageView2.setOnClickListener {
            binding.ellipsisTip.visibility = View.VISIBLE
        }

        binding.imageView3.setOnClickListener {
            binding.entryTip.visibility = View.VISIBLE
        }

        binding.imageSettings.setOnClickListener {
            binding.tvSettings.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}