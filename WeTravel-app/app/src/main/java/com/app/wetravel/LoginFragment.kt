package com.app.wetravel


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.wetravel.databinding.LoginBinding


class LoginFragment: Fragment(),OkHttpLogin.OkHttpCallback{
    private var _binding:LoginBinding?=null
    private val binding get()=_binding!!
    override fun onSuccess(result: String) {
        requireActivity().onBackPressedDispatcher.onBackPressed()
        // 处理请求成功的逻辑
    }

    override fun onFailure(error: String) {
        val dialog = ErrorBottomSheetDialog.newInstance()
        dialog.show(childFragmentManager, "ErrorDialog")
        // 处理请求失败的逻辑
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding= LoginBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.imageButton9.setOnClickListener(){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.textView10.setOnClickListener(){
            replaceFragment(RegisterFragment())
        }

        binding.button4.setOnClickListener() {
            val username = binding.editTextTextUsername.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val okHttpTest = OkHttpTest(username, password)
            okHttpTest.execute()
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