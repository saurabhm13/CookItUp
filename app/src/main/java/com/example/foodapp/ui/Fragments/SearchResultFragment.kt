package com.example.foodapp.ui.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.adapters.MealsAdapter
import com.example.foodapp.databinding.FragmentSearchResultBinding
import com.example.foodapp.ui.Activity.MainActivity
import com.example.foodapp.ui.Activity.MealActivity
import com.example.foodapp.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchRecyclerViewAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchResultBinding.inflate(inflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        binding.imageSearchArrow.setOnClickListener {
            searchMeal()
        }

        observeSearchMealLiveData()

        onMealItemClick()

        // Auto search
        var searchJob: Job? = null
        binding.edSearchBox.addTextChangedListener {searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeal(searchQuery.toString())
            }
        }
    }

    private fun onMealItemClick() {
        searchRecyclerViewAdapter.onItemClick = {
            val intoMealDetail = Intent(context, MealActivity::class.java)
            intoMealDetail.putExtra("Meal_Id", it.idMeal)
            intoMealDetail.putExtra("Meal_Name", it.strMeal)
            intoMealDetail.putExtra("Meal_Thumb", it.strMealThumb)
            startActivity(intoMealDetail)
        }
    }

    private fun observeSearchMealLiveData() {
        viewModel.observeSearchMealLiveData().observe(viewLifecycleOwner, Observer { mealsList ->
            searchRecyclerViewAdapter.differ.submitList(mealsList)
        })
    }

    private fun searchMeal() {
        val searchQuery = binding.edSearchBox.text.toString()

        if (searchQuery.isNotEmpty()){
            viewModel.searchMeal(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerViewAdapter = MealsAdapter()
        binding.rvSearchMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchRecyclerViewAdapter
        }
    }
}