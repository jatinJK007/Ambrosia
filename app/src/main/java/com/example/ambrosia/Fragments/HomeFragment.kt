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
import com.example.ambrosia.Adaptors.DrinkAdap
import com.example.ambrosia.Adaptors.RecommAdap
import com.example.ambrosia.Adaptors.catAdap
import com.example.ambrosia.DetailedActivity
import com.example.ambrosia.Utils.RetroInstance
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
    private lateinit var myAdapterD :DrinkAdap
    private lateinit var myAdapterR : RecommAdap
    private lateinit var rvRecom :RecyclerView
    private lateinit var rvD:RecyclerView
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
//        initailized drink adpater
        rvD = binding.rvDrink
        rvD.layoutManager=
            LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        myAdapterD= DrinkAdap(this@HomeFragment, emptyList())
        rvD.adapter= myAdapterD

//        initailized category adpater
        rv = binding.rvCategory
        rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        myAdapter = catAdap(this@HomeFragment, emptyList())
        rv.adapter = myAdapter

//        initailized Recommendation adpater
        rvRecom = binding.rvRecomm
        rvRecom.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        myAdapterR = RecommAdap(this@HomeFragment, emptyList())
        rvRecom.adapter= myAdapterR
        if (!isDataLoaded){
            Log.d("homefragment", "onViewCreated: fetchcategories called sucessfully")
            fetchCategories()
            fetchDrinks()
            fetchRecomm()
        }
    }

    private fun fetchRecomm() {
        coroutineScope.launch {
            val response = try {
                RetroInstance.api.getRecomm()
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
                if (response.meals.isNotEmpty()) {
                    Log.d("HomeFragment", "Category list size: ${response.meals.size}")
                    myAdapterR.recommList = response.meals
                    myAdapterR.notifyDataSetChanged() // Notifying the adapter about dataset change

                } else {
                    Log.w("HomeFragment", "Category list is empty")
                    Toast.makeText(requireContext(), "No Categories Found", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun fetchDrinks() {
        coroutineScope.launch {
            val response = try {
                RetroInstance.dapi.getDrink()
            }catch (e: HttpException) {
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
                if (response.drinks.isNotEmpty()) {
                    Log.d("HomeFragment", "Category list size: ${response.drinks.size}")
                    myAdapterD.drinkList = response.drinks
                    myAdapterD.notifyDataSetChanged() // Notifying the adapter about dataset change

                } else {
                    Log.w("HomeFragment", "Category list is empty")
                    Toast.makeText(requireContext(), "No Categories Found", Toast.LENGTH_SHORT)
                        .show()
                }
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

//                    setting on clicklistner on item
                    myAdapter.onItemClick = { category ->
                        Log.d("HomeFragment", "Clicked on category: ${category.strCategory}")
                        val intent = Intent(requireContext(), DetailedActivity::class.java).apply {
                            putExtra("IMAGE_URL", category.strCategoryThumb)
                            putExtra("TITLE", category.strCategory)
                            putExtra("DESCRIPTION", category.strCategoryDescription)
                        }
                        startActivity(intent)
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