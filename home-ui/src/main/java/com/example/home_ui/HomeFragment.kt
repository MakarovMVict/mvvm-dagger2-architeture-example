package com.example.home_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.ds_core_android.BaseFragment
import com.example.ds_core_android.BottomNavTabsStream
import com.example.home_ui.databinding.FragmentHomeBinding
import javax.inject.Inject


class HomeFragment : BaseFragment() {
    @Inject
    lateinit var bottomNavTabsStream: BottomNavTabsStream

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding?.root
    }





}