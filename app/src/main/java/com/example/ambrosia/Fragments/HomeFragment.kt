package com.example.ambrosia.Fragments

import android.content.Intent
import retrofit2.HttpException
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ambrosia.Adaptors.catAdap
import com.example.ambrosia.DetailedActivity
import com.example.ambrosia.Models.Category
import com.example.ambrosia.RetroInstance
import com.example.ambrosia.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var myAdapter: catAdap
    private lateinit var rv :RecyclerView
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private var isDataLoaded = false // Track if data has been loaded

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv = binding.rvCategory
        rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        myAdapter = catAdap(this@HomeFragment, emptyList())
        rv.adapter = myAdapter
        if (!isDataLoaded){
            Log.d("homefragment", "onViewCreated: fetchcategories called sucessfully")
            fetchCategories()

//            implemented the on click functionality after clicking on each item on main fragment it will redirect to detailed activity
            myAdapter.onItemClick = { Category ->
                val intent = Intent(activity, DetailedActivity::class.java)
                activity?.startActivity(intent)
            }
        }
    }

    private fun fetchCategories() {
        coroutineScope.launch {
            val response = try {
                RetroInstance.api.getCategory()
            } catch (e: HttpException) {
                Log.e("HomeFragment", "Http Error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show()
                }
                return@launch
            } catch (e: IOException) {
                Log.e("HomeFragment", "I/O Error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show()
                }
                return@launch
            } catch (e: Exception) {
                Log.e("HomeFragment", "Generic Error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "An Error Occured", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            withContext(Dispatchers.Main) {
                if (response.categories.isNotEmpty()) {
                    Log.d("HomeFragment", "Category list size: ${response.categories.size}")
                    myAdapter.catlist = response.categories
                    myAdapter.notifyDataSetChanged() // Notifying the adapter about dataset change
                    myAdapter.onItemClick = { category ->
                        Log.d("HomeFragment", "Clicked on category: ${category.strCategory}")
                    }
                } else {
                    Log.w("HomeFragment", "Category list is empty")
                    Toast.makeText(requireContext(), "No Categories Found", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job.cancel()
    }
}