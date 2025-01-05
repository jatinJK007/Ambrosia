package com.example.ambrosia.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ambrosia.Adaptors.catAdap
import com.example.ambrosia.RetroInstance
import com.example.ambrosia.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var myAdapter: catAdap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = binding.rvCategory
        val response = RetroInstance.api.getCategory()

        if (response.isSuccessful && response.body()!= null){
            myAdapter=catAdap(this@HomeFragment, catlist = response)
            rv.adapter= myAdapter
            rv.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)



        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}