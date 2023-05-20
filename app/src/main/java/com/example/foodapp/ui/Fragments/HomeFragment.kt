package com.example.foodapp.ui.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.adapters.CategoryAdapter
import com.example.foodapp.adapters.MostPopularAdapter
import com.example.foodapp.data.MealsByCategory
import com.example.foodapp.data.Meal
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.ui.Activity.CategoryMealsActivity
import com.example.foodapp.ui.Activity.MealActivity
import com.example.foodapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]

        popularItemAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observePopularItems()
        preparePopularItemRecyclerView()
        onPopularItemClick()

        homeMvvm.getCategories()
        observeCategoryItems()
        prepareCategoryItemRecyclerView()
        onCategoryItemclick()



    }

    private fun onCategoryItemclick() {
        categoriesAdapter.onItemClick = {category ->
            val inToCategory = Intent(activity, CategoryMealsActivity::class.java)
            inToCategory.putExtra("Category_Name", category.strCategory)
            startActivity(inToCategory)
        }
    }

    private fun prepareCategoryItemRecyclerView() {
        categoriesAdapter = CategoryAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoryItems() {
        homeMvvm.observeCategoryLiveData().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategoryList(categories)
        }
    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick = {meal ->
            val inToMealDetail = Intent(activity, MealActivity::class.java)
            inToMealDetail.putExtra("Meal_Id", meal.idMeal)
            inToMealDetail.putExtra("Meal_Name", meal.strMeal)
            inToMealDetail.putExtra("Meal_Thumb", meal.strMealThumb)
            startActivity(inToMealDetail)
        }
    }

    private fun preparePopularItemRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemAdapter
        }
    }

    private fun observePopularItems() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList->
            popularItemAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intoMealDetail = Intent(activity, MealActivity::class.java)
            intoMealDetail.putExtra("Meal_Id", randomMeal.idMeal)
            intoMealDetail.putExtra("Meal_Name", randomMeal.strMeal)
            intoMealDetail.putExtra("Meal_Thumb", randomMeal.strMealThumb)
            startActivity(intoMealDetail)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            randomMeal = meal
        }
    }
}