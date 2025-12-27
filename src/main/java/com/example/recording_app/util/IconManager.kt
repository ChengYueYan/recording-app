package com.example.recording_app.util

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

object IconManager {
    // Map of icon emoji to component name (alias name without package prefix)
    private val iconComponentMap = mapOf(
        "💰" to "Icon1",
        "📱" to "Icon2",
        "🎨" to "Icon3",
        "⭐" to "Icon4",
        "🌸" to "Icon5",
        "🎯" to "Icon6",
        "💎" to "Icon7",
        "🎭" to "Icon8",
        "🌟" to "Icon9",
        "🎪" to "Icon10",
        "🔮" to "Icon11",
        "💫" to "Icon12"
    )
    
    fun changeAppIcon(context: Context, selectedIcon: String): Boolean {
        return try {
            val packageManager = context.packageManager
            val packageName = context.packageName
            
            // Disable all aliases first
            iconComponentMap.values.forEach { aliasName ->
                val componentName = ComponentName(packageName, "$packageName.$aliasName")
                packageManager.setComponentEnabledSetting(
                    componentName,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
                )
            }
            
            // Enable the selected alias
            val selectedAlias = iconComponentMap[selectedIcon] ?: return false
            val selectedComponent = ComponentName(packageName, "$packageName.$selectedAlias")
            packageManager.setComponentEnabledSetting(
                selectedComponent,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
            
            // Disable the main activity
            val mainActivity = ComponentName(packageName, "$packageName.MainActivity")
            packageManager.setComponentEnabledSetting(
                mainActivity,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
            
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    fun restoreMainActivity(context: Context): Boolean {
        return try {
            val packageManager = context.packageManager
            val packageName = context.packageName
            
            // Disable all aliases
            iconComponentMap.values.forEach { aliasName ->
                val componentName = ComponentName(packageName, "$packageName.$aliasName")
                packageManager.setComponentEnabledSetting(
                    componentName,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
                )
            }
            
            // Enable main activity
            val mainActivity = ComponentName(packageName, "$packageName.MainActivity")
            packageManager.setComponentEnabledSetting(
                mainActivity,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
            
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

