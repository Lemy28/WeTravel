package com.app.wetravel

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.wetravel.databinding.SettingBinding
import UserManager
class SettingFrgment: Fragment(){
    private var _binding: SettingBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= SettingBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.imageButton10.setOnClickListener(){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.imageButton14.setOnClickListener(){
            UserManager.logout()
            requireActivity().onBackPressedDispatcher.onBackPressed()

        }
        return view
    }
}