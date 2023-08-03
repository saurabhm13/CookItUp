package com.example.cookitup.ui.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cookitup.R
import com.example.cookitup.adapters.IngredientAdapter
import com.example.cookitup.data.IngredientData
import com.example.cookitup.data.Meal
import com.example.cookitup.databinding.ActivityMealBinding
import com.example.cookitup.db.MealDatabase
import com.example.cookitup.viewmodel.MealDetailViewModel
import com.example.cookitup.viewmodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding

    private lateinit var mealName: String
    private lateinit var mealId: String
    private lateinit var mealThumb: String
    private lateinit var mealLink: String

    private lateinit var mealDetailViewModel: MealDetailViewModel
    private lateinit var ingredientAdapter: IngredientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)

        mealDetailViewModel = ViewModelProvider(this, viewModelFactory)[MealDetailViewModel::class.java]

//        mealDetailViewModel = ViewModelProviders.of(this)[MealDetailViewModel::class.java]

        getMealInfoFromIntent()

        setInfoInViews()

        mealDetailViewModel.getMealDetail(mealId)
        observeMealDetailsLiveData()

        binding.backMealDetail.setOnClickListener {
            finish()
        }

//        onYoutubeImageClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.favoriteMealDetail.setOnClickListener {
            mealToSave?.let { it1 ->
                mealDetailViewModel.insertMeal(it1)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_LONG).show()
            }
        }
    }

//    private fun onYoutubeImageClick() {
//        binding.imgYoutube.setOnClickListener {
//            val inToYoutube = Intent(Intent.ACTION_VIEW, Uri.parse(mealLink))
//            startActivity(inToYoutube)
//        }
//    }

    private var mealToSave: Meal? = null
    private fun observeMealDetailsLiveData() {
        mealDetailViewModel.observeMealDetailLiveData().observe(this
        ) { t ->
            val meal = t!!
            mealToSave = meal

//            binding.tvCategoryInfo.text = "Category: ${meal.strCategory}"
//            binding.tvAreaInfo.text = "Location: ${meal.strArea}"
            binding.instruction.text = meal.strInstructions

            mealLink = meal.strYoutube.toString()

//            setContentVisibility()

            ingredientAdapter = IngredientAdapter()

            binding.rvIngredient.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = ingredientAdapter
            }

            ingredientAdapter.setIngredientList(getIngredientList(t))
        }
    }

//    private fun setContentVisibility() {
//        binding.loadingCategory.visibility = View.GONE
//        binding.loadingArea.visibility = View.GONE
//        binding.loadingInstruction.visibility = View.GONE
//        binding.loadingContent1.visibility = View.GONE
//        binding.loadingContent2.visibility = View.GONE
//        binding.loadingContent3.visibility = View.GONE
//
//        binding.tvCategoryInfo.visibility = View.VISIBLE
//        binding.tvInstructions.visibility = View.VISIBLE
//        binding.tvContent.visibility = View.VISIBLE
//        binding.tvAreaInfo.visibility = View.VISIBLE
//    }

    private fun setInfoInViews() {
        Glide.with(application)
            .load(mealThumb)
            .into(binding.mealImage)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra("Meal_Id")!!
        mealName = intent.getStringExtra("Meal_Name")!!
        mealThumb = intent.getStringExtra("Meal_Thumb")!!
    }

    private fun getIngredientList(meal: Meal): List<IngredientData> {

        val nonNullIngredients = mutableListOf<IngredientData>()
        val properties = Meal::class.java.declaredFields


        for (i in 1..20) {
            val ingredientField = properties.find { it.name == "strIngredient$i" }
            val measureField = properties.find { it.name == "strMeasure$i" }

            val ingredient = ingredientField?.get(meal) as? String
            val measure = measureField?.get(meal) as? String

            if (!ingredient.isNullOrBlank()) {

                nonNullIngredients.add(IngredientData(ingredient, measure?: ""))
            } else {
                // Stop processing once a null ingredient is encountered
                break
            }
        }

        return nonNullIngredients
    }
}