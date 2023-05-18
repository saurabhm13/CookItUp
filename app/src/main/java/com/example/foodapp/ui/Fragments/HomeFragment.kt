package com.example.foodapp.ui.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodapp.data.Meal
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.retrofit.MealApi
import com.example.foodapp.ui.Activity.MealActivity
import com.example.foodapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]
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