package com.app.wetravel


import OrderListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.wetravel.databinding.LoginBinding
import com.app.wetravel.databinding.OrderBinding
import com.app.wetravel.databinding.PersonalFragmentBinding

class OrderFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderListAdapter
    private var _binding: OrderBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= OrderBinding.inflate(inflater,container,false)
        val view = binding.root
        recyclerView=binding.recyclerView
        adapter = OrderListAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        return view
    }
}