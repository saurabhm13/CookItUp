package com.example.cookitup.ui.Fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.cookitup.databinding.FragmentMealBottomSheetBinding
import com.example.cookitup.ui.Activity.MainActivity
import com.example.cookitup.ui.Activity.MealActivity
import com.example.cookitup.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "param1"

class MealBottomSheetFragment : BottomSheetDialogFragment() {

    private var mealId: String? = null
    private var mealName: String? = null
    private var mealThumb: String? = null
    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let { viewModel.getMealById(it) }

        observeMealLiveData()

        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener{
            if (mealName != null && mealThumb != null){
                val intoMealActivity = Intent(activity, MealActivity::class.java)
                intoMealActivity.apply {
                    putExtra("Meal_Id", mealId)
                    putExtra("Meal_Name", mealName)
                    putExtra("Meal_Thumb", mealThumb)
                }
                startActivity(intoMealActivity)
            }
        }
    }

    private fun observeMealLiveData() {
        viewModel.observeBottomSheetLiveData().observe(viewLifecycleOwner, Observer { meal ->
            Glide.with(this)
                .load(meal.strMealThumb)
                .into(binding.imgBottomSheet)

            binding.tvMealCountry.text = meal.strArea
            binding.tvMealCategory.text = meal.strCategory
            binding.tvMealNameInBtmsheet.text = meal.strMeal

            mealName = meal.strMeal
            mealThumb = meal.strMealThumb
        })
    }

    companion object {
        @JvmStatic fun newInstance(param1: String) =
                MealBottomSheetFragment().apply {
                    arguments = Bundle().apply {
                        putString(MEAL_ID, param1)
                    }
                }
    }
}