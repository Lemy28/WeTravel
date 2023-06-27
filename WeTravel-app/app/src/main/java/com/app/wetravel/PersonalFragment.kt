package com.app.wetravel

import InformationFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.wetravel.databinding.PersonalFragmentBinding

class PersonalFragment:Fragment() {
    private var _binding:PersonalFragmentBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= PersonalFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.textView12.setOnClickListener(){
            replaceFragment(LoginFragment())
        }
        binding.imageButton2.setOnClickListener(){
            replaceFragment(SettingFrgment())
        }

        return view
    }
    private fun replaceFragment(fragment:Fragment){
        val fragmentManager=activity?.supportFragmentManager!!
        val transaction=fragmentManager.beginTransaction()
        transaction.replace(R.id.contentfragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}