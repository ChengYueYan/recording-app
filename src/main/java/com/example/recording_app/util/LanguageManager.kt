package com.example.recording_app.util

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import java.util.Locale

object LanguageManager {
    private const val PREFS_NAME = "language_prefs"
    private const val KEY_LANGUAGE = "selected_language"
    private const val KEY_IS_FIRST_LAUNCH = "is_first_launch"
    
    const val LANGUAGE_CHINESE = "zh"
    const val LANGUAGE_ENGLISH = "en"
    const val LANGUAGE_DEFAULT = LANGUAGE_CHINESE
    
    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    fun isFirstLaunch(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_IS_FIRST_LAUNCH, true)
    }
    
    fun setFirstLaunchCompleted(context: Context) {
        getSharedPreferences(context).edit()
            .putBoolean(KEY_IS_FIRST_LAUNCH, false)
            .apply()
    }
    
    fun getSavedLanguage(context: Context): String {
        return getSharedPreferences(context).getString(KEY_LANGUAGE, LANGUAGE_DEFAULT) ?: LANGUAGE_DEFAULT
    }
    
    fun saveLanguage(context: Context, language: String) {
        getSharedPreferences(context).edit()
            .putString(KEY_LANGUAGE, language)
            .apply()
    }
    
    fun setAppLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        
        return context.createConfigurationContext(configuration)
    }
    
    fun updateConfiguration(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        
        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
    
    fun getLocale(language: String): Locale {
        return Locale(language)
    }
    
    fun getLanguageDisplayName(context: Context, language: String): String {
        return when (language) {
            LANGUAGE_CHINESE -> context.getString(com.example.recording_app.R.string.language_chinese)
            LANGUAGE_ENGLISH -> context.getString(com.example.recording_app.R.string.language_english)
            else -> language
        }
    }
}

