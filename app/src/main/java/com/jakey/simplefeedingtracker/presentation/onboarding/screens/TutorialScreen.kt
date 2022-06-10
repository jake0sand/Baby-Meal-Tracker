package com.jakey.simplefeedingtracker.presentation.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.jakey.simplefeedingtracker.R
import com.jakey.simplefeedingtracker.databinding.FragmentFirstScreenBinding
import com.jakey.simplefeedingtracker.databinding.FragmentTutorialScreenBinding


class TutorialScreen : Fragment() {

    private var _binding: FragmentTutorialScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialScreenBinding.inflate(inflater, container, false)




        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}