package com.jakey.simplefeedingtracker.presentation.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.jakey.simplefeedingtracker.R
import com.jakey.simplefeedingtracker.databinding.FragmentSplashBinding
import com.jakey.simplefeedingtracker.databinding.FragmentViewPagerBinding
import com.jakey.simplefeedingtracker.presentation.onboarding.screens.FirstScreen
import com.jakey.simplefeedingtracker.presentation.onboarding.screens.SecondScreen
import com.jakey.simplefeedingtracker.presentation.onboarding.screens.ThirdScreen
import com.jakey.simplefeedingtracker.presentation.onboarding.screens.TutorialScreen

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        val fragmentList = arrayListOf<Fragment>(
            TutorialScreen(),
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}