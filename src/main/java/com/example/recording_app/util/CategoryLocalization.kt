package com.example.recording_app.util

import android.content.Context
import com.example.recording_app.R

object CategoryLocalization {
    /**
     * Maps category name to string resource ID
     */
    private val categoryNameToResId = mapOf(
        "餐饮" to R.string.category_food,
        "交通" to R.string.category_transport,
        "购物" to R.string.category_shopping,
        "娱乐" to R.string.category_entertainment,
        "医疗" to R.string.category_medical,
        "教育" to R.string.category_education,
        "住房" to R.string.category_housing,
        "其他" to R.string.category_other,
        "工资" to R.string.category_salary,
        "奖金" to R.string.category_bonus,
        "投资" to R.string.category_investment,
        "其他收入" to R.string.category_other_income,
    )
    
    /**
     * Get localized category name from context
     */
    fun getLocalizedCategoryName(context: Context, categoryName: String): String {
        val resId = categoryNameToResId[categoryName]
        return if (resId != null) {
            context.getString(resId)
        } else {
            categoryName // Fallback to original name if not found
        }
    }
}

