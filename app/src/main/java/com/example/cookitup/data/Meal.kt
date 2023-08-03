package com.example.cookitup.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mealInformation")
data class Meal(
    val dateModified: Any?,
    @PrimaryKey
    var idMeal: String,
    val strArea: String?,
    val strCategory: String?,
    val strCreativeCommonsConfirmed: Any?,
    val strDrinkAlternate: Any?,
    val strImageSource: Any?,
    @JvmField
    val strIngredient1: String?,
    @JvmField
    val strIngredient10: String?,
    @JvmField
    val strIngredient11: String?,
    @JvmField
    val strIngredient12: String?,
    @JvmField
    val strIngredient13: String?,
    @JvmField
    val strIngredient14: String?,
    @JvmField
    val strIngredient15: String?,
    @JvmField
    val strIngredient16: String?,
    @JvmField
    val strIngredient17: String?,
    @JvmField
    val strIngredient18: String?,
    @JvmField
    val strIngredient19: String?,
    @JvmField
    val strIngredient2: String?,
    @JvmField
    val strIngredient20: String?,
    @JvmField
    val strIngredient3: String?,
    @JvmField
    val strIngredient4: String?,
    @JvmField
    val strIngredient5: String?,
    @JvmField
    val strIngredient6: String?,
    @JvmField
    val strIngredient7: String?,
    @JvmField
    val strIngredient8: String?,
    @JvmField
    val strIngredient9: String?,
    val strInstructions: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    @JvmField
    val strMeasure1: String?,
    @JvmField
    val strMeasure10: String?,
    @JvmField
    val strMeasure11: String?,
    @JvmField
    val strMeasure12: String?,
    @JvmField
    val strMeasure13: String?,
    @JvmField
    val strMeasure14: String?,
    @JvmField
    val strMeasure15: String?,
    @JvmField
    val strMeasure16: String?,
    @JvmField
    val strMeasure17: String?,
    @JvmField
    val strMeasure18: String?,
    @JvmField
    val strMeasure19: String?,
    @JvmField
    val strMeasure2: String?,
    @JvmField
    val strMeasure20: String?,
    @JvmField
    val strMeasure3: String?,
    @JvmField
    val strMeasure4: String?,
    @JvmField
    val strMeasure5: String?,
    @JvmField
    val strMeasure6: String?,
    @JvmField
    val strMeasure7: String?,
    @JvmField
    val strMeasure8: String?,
    @JvmField
    val strMeasure9: String?,
    val strSource: String?,
    val strTags: Any?,
    val strYoutube: String?
)