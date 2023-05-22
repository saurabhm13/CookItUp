package com.example.foodapp.ui.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodapp.adapters.FavoriteMealsAdapter
import com.example.foodapp.databinding.FragmentFavouriteBinding
import com.example.foodapp.ui.Activity.MainActivity
import com.example.foodapp.ui.Activity.MealActivity
import com.example.foodapp.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

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

        // Movement of item
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.delete(favouriteAdapter.differ.currentList[position])

                Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo", View.OnClickListener {
                        viewModel.insertMeal(favouriteAdapter.differ.currentList[position])
                    }
                ).show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)
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