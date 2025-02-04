package com.example.ambrosia.Fragments

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
import com.example.ambrosia.Models.dcCat
import com.example.ambrosia.RetroInstance
import com.example.ambrosia.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
                    Log.d("HomeFragment", "after cat;ist called line 2")
                    myAdapter.notifyDataSetChanged()
                    Log.d("HomeFragment", "after notifydtasetchange")
                    myAdapter.onItemClick = { category ->
                        // Handle item click here
                        Log.d("HomeFragment", "Clicked on category: ${category.strCategory}")
                    }
                } else {
                    Log.w("HomeFragment", "Category list is empty")
                    Toast.makeText(requireContext(), "No Categories Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


//        Log.d("TAG", "onViewCreated: succesfully and item displayed in fragment")
//        GlobalScope.launch(Dispatchers.IO){
//            val response = try{
//                RetroInstance.api.getCategory()
//            }catch (e: HttpException) {
//                Log.e("TAG", "Http Error: ${e.message}", e)
//                return@launch
//            } catch (e: IOException) {
//                Log.e("TAG", "I/O Error: ${e.message}", e)
//                return@launch
//            } catch (e: Exception) {
//                Log.e("TAG", "Generic Error: ${e.message}", e)
//                return@launch
//            }
//            withContext(Dispatchers.Main) {
//                if (response != null) {
//                    Log.d("TAG", "onViewCreated: dispatcher main")
//
//                    myAdapter = catAdap(this@HomeFragment, response.categories)
//                    rv.adapter = myAdapter
//                    rv.layoutManager = LinearLayoutManager(requireContext())
//                }
//            }
//        }
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job.cancel()
    }
}