package com.example.cookitup.retrofit

import com.example.cookitup.data.CategoryList
import com.example.cookitup.data.MealsByCategoryList
import com.example.cookitup.data.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i")id:String): Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c")categoryName: String): Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategories(@Query("c") categoryName: String): Call<MealsByCategoryList>

    @GET("search.php")
    fun getSearchResult(@Query("s") mealName: String): Call<MealList>
}