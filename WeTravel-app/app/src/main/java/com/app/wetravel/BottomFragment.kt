package com.app.wetravel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.wetravel.R
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

            replaceFragment(HomeFragment())
            binding.button.setImageResource(R.drawable.home_selected)
            binding.button2.setImageResource(R.drawable.wishlist);
            binding.button3.setImageResource(R.drawable.person);

        }
        binding.button2.setOnClickListener(){
            replaceFragment(WishListFragment())
            binding.button2.setImageResource(R.drawable.wishlist_selected)
            binding.button.setImageResource(R.drawable.home)
            binding.button3.setImageResource(R.drawable.person)
        }
        binding.button3.setOnClickListener(){
            replaceFragment(PersonalFragment())
            binding.button3.setImageResource(R.drawable.person_selected)
            binding.button.setImageResource(R.drawable.home)
            binding.button2.setImageResource(R.drawable.wishlist)
        }
        return view
    }
    private fun replaceFragment(fragment:Fragment){
        val fragmentManager=activity?.supportFragmentManager!!
        val transaction=fragmentManager.beginTransaction()
        transaction.replace(R.id.content,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
