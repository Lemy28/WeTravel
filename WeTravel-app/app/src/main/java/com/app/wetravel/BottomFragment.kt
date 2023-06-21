package com.app.wetravel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.wetravel.databinding.BottomFragmentBinding


class BottomFragment :Fragment(){
    private var _binding: BottomFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.button.setOnClickListener(){
            replaceFragment(ContentFragment())
        }
        binding.button2.setOnClickListener(){
            replaceFragment(WishListFragment())
        }
        binding.button3.setOnClickListener(){
            replaceFragment(PersonalFragment())
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
