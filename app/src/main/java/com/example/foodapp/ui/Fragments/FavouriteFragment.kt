package com.example.foodapp.ui.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.adapters.FavoriteMealsAdapter
import com.example.foodapp.databinding.FragmentFavouriteBinding
import com.example.foodapp.ui.Activity.MainActivity
import com.example.foodapp.ui.Activity.MealActivity
import com.example.foodapp.viewmodel.HomeViewModel

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favouriteAdapter: FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFavorites()
        prepareFavoriteRecyclerView()
        onFavoriteMealItemClick()
    }

    private fun onFavoriteMealItemClick() {
        favouriteAdapter.onItemClick = {meal ->
            val intoMealDetail = Intent(activity, MealActivity::class.java)
            intoMealDetail.putExtra("Meal_Id", meal.idMeal)
            intoMealDetail.putExtra("Meal_Name", meal.strMeal)
            intoMealDetail.putExtra("Meal_Thumb", meal.strMealThumb)
            startActivity(intoMealDetail)
        }
    }

    private fun prepareFavoriteRecyclerView() {
        favouriteAdapter = FavoriteMealsAdapter()
        binding.favRecView.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favouriteAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavoriteMealsLiveData().observe(viewLifecycleOwner, Observer { meals ->
            favouriteAdapter.differ.submitList(meals)
        })
    }
}