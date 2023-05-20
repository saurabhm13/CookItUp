package com.example.foodapp.retrofit

import com.example.foodapp.data.CategoryList
import com.example.foodapp.data.MealsByCategoryList
import com.example.foodapp.data.MealList
import com.example.foodapp.data.MealsByCategory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i")id:String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c")categoryName: String): Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategories(@Query("c") categoryName: String): Call<MealsByCategoryList>
}