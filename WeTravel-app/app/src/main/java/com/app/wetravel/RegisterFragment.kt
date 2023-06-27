package com.app.wetravel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.wetravel.databinding.SignupBinding

class RegisterFragment:Fragment(),OkHttpSignup.OkHttpCallback {
    private var _binding: SignupBinding?=null
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
        _binding= SignupBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.imageButton9.setOnClickListener(){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.textView10.setOnClickListener(){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.button4.setOnClickListener() {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmpassword=binding.editTextPassword2.text.toString()
            if(password==confirmpassword){
                val okHttpTest = OkHttpSignup(username, password,confirmpassword)
                okHttpTest.setCallback(this)
                okHttpTest.execute()
            }
            else
            {
                //TODO 提示前后输入的密码不一致，请重新输入。
            }
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