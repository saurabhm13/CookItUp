package com.example.foodapp.ui.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.adapters.CategoryMealsAdapter
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.viewmodel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMealViewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        prepareCategoryRecyclerView()

        categoryMealViewModel.getMealsByCategory(intent.getStringExtra("Category_Name")!!)

        categoryMealViewModel.observeMealsLiveData().observe(this, Observer { mealsList ->
            binding.tvCategoryCount.text = mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)
        })

        onCategoryMealItemClick()
    }

    private fun onCategoryMealItemClick() {
        categoryMealsAdapter.onItemClick = {
            val intoMealDetail = Intent(this, MealActivity::class.java)
            intoMealDetail.putExtra("Meal_Id", it.idMeal)
            intoMealDetail.putExtra("Meal_Name", it.strMeal)
            intoMealDetail.putExtra("Meal_Thumb", it.strMealThumb)
            startActivity(intoMealDetail)
        }
    }

    private fun prepareCategoryRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter

        }
    }
}