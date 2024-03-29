package com.example.cookitup.ui.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cookitup.R
import com.example.cookitup.TestActivity
import com.example.cookitup.adapters.CategoryAdapter
import com.example.cookitup.adapters.MostPopularAdapter
import com.example.cookitup.data.MealsByCategory
import com.example.cookitup.data.Meal
import com.example.cookitup.databinding.FragmentHomeBinding
import com.example.cookitup.ui.Activity.CategoryMealsActivity
import com.example.cookitup.ui.Activity.MainActivity
import com.example.cookitup.ui.Activity.MealActivity
import com.example.cookitup.ui.Fragments.bottomsheet.MealBottomSheetFragment
import com.example.cookitup.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
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

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItems()
        preparePopularItemRecyclerView()
        onPopularItemClick()
        onPopularItemLongClick()

        viewModel.getCategories()
        observeCategoryItems()
        prepareCategoryItemRecyclerView()
        onCategoryItemClick()

        onSearchIconClick()

    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchResultFragment)
        }
    }

    private fun onPopularItemLongClick() {
        popularItemAdapter.onLongItemClick = { meal ->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager, "Meal Info")
        }
    }

    private fun onCategoryItemClick() {
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
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner) { categories ->
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
        popularItemAdapter = MostPopularAdapter()
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemAdapter
        }
    }

    private fun observePopularItems() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner
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
        viewModel.observeRandomMealData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            randomMeal = meal
        }
    }
}