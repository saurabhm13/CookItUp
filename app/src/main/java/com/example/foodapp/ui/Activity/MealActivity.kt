package com.example.foodapp.ui.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.data.Meal
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.viewmodel.MealDetailViewModel
import java.util.Objects

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding

    private lateinit var mealName: String
    private lateinit var mealId: String
    private lateinit var mealThumb: String
    private lateinit var mealLink: String

    private lateinit var mealDetailViewModel: MealDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealDetailViewModel = ViewModelProviders.of(this)[MealDetailViewModel::class.java]

        getMealInfoFromIntent()

        setInfoInViews()

        mealDetailViewModel.getMealDetail(mealId)
        observeMealDetailsLiveData()

        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val inToYoutube = Intent(Intent.ACTION_VIEW, Uri.parse(mealLink))
            startActivity(inToYoutube)
        }
    }

    private fun observeMealDetailsLiveData() {
        mealDetailViewModel.observeMealDetailLiveData().observe(this, object : Observer<Meal>{
            @SuppressLint("SetTextI18n")
            override fun onChanged(t: Meal?) {
                val meal = t!!

                binding.tvCategoryInfo.text = "Category: ${meal.strCategory}"
                binding.tvAreaInfo.text = "Location: ${meal.strArea}"
                binding.tvInstructions.text = meal.strInstructions

                mealLink = meal.strYoutube
            }
        })
    }

    private fun setInfoInViews() {
        Glide.with(application)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra("Meal_Id")!!
        mealName = intent.getStringExtra("Meal_Name")!!
        mealThumb = intent.getStringExtra("Meal_Thumb")!!
    }
}