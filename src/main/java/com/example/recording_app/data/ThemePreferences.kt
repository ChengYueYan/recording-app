package com.example.recording_app.data

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.Color
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object ThemePreferences {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_PRIMARY_COLOR = "primary_color"
    private const val KEY_BACKGROUND_IMAGE = "background_image_path"
    private const val KEY_ICON_IMAGE = "icon_image_path"
    private const val KEY_SELECTED_ICON = "selected_icon"
    private const val DEFAULT_PRIMARY_COLOR = 0xFF6366F1 // Indigo

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun savePrimaryColor(context: Context, color: Long) {
        getSharedPreferences(context).edit().putLong(KEY_PRIMARY_COLOR, color).apply()
    }

    fun getPrimaryColor(context: Context): Long {
        return getSharedPreferences(context).getLong(KEY_PRIMARY_COLOR, DEFAULT_PRIMARY_COLOR)
    }

    fun getPrimaryColorAsColor(context: Context): Color {
        return Color(getPrimaryColor(context))
    }

    fun saveBackgroundImageUri(context: Context, uri: Uri): String? {
        return try {
            val imageFile = File(context.filesDir, "custom_background.jpg")

            // 🔧 如果旧文件存在,先删除
            if (imageFile.exists()) {
                imageFile.delete()
            }

            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(imageFile).use { output ->
                    // 🔧 添加缓冲区提高性能
                    val buffer = ByteArray(8192)
                    var bytesRead: Int
                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        output.write(buffer, 0, bytesRead)
                    }
                    output.flush()
                }
            }

            // 🔧 验证文件是否保存成功
            if (imageFile.exists() && imageFile.length() > 0) {
                android.util.Log.d("ThemePreferences", "Image saved: ${imageFile.absolutePath}, size: ${imageFile.length()}")
                getSharedPreferences(context).edit()
                    .putString(KEY_BACKGROUND_IMAGE, imageFile.absolutePath)
                    .apply()
                imageFile.absolutePath
            } else {
                android.util.Log.e("ThemePreferences", "Image file not created or empty")
                null
            }
        } catch (e: Exception) {
            android.util.Log.e("ThemePreferences", "Error saving background image", e)
            e.printStackTrace()
            null
        }
    }

    fun getBackgroundImagePath(context: Context): String? {
        val path = getSharedPreferences(context).getString(KEY_BACKGROUND_IMAGE, null)
        return if (path != null && File(path).exists()) path else null
    }

    fun clearBackgroundImage(context: Context) {
        getBackgroundImagePath(context)?.let { path ->
            File(path).delete()
        }
        getSharedPreferences(context).edit().remove(KEY_BACKGROUND_IMAGE).apply()
    }

    fun saveIconImageUri(context: Context, uri: Uri): String? {
        return try {
            // Resize icon to 512x512 (standard launcher icon size)
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            
            if (originalBitmap != null) {
                // Resize bitmap to 512x512
                val resizedBitmap = Bitmap.createScaledBitmap(
                    originalBitmap, 
                    512, 
                    512, 
                    true
                )
                originalBitmap.recycle()
                
                // Save resized bitmap
                val iconFile = File(context.filesDir, "custom_icon.png")
                FileOutputStream(iconFile).use { out ->
                    resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                }
                resizedBitmap.recycle()
                
                getSharedPreferences(context).edit()
                    .putString(KEY_ICON_IMAGE, iconFile.absolutePath)
                    .apply()
                iconFile.absolutePath
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getIconImagePath(context: Context): String? {
        val path = getSharedPreferences(context).getString(KEY_ICON_IMAGE, null)
        return if (path != null && File(path).exists()) path else null
    }

    fun clearIconImage(context: Context) {
        getIconImagePath(context)?.let { path ->
            File(path).delete()
        }
        getSharedPreferences(context).edit().remove(KEY_ICON_IMAGE).apply()
    }

    fun saveSelectedIcon(context: Context, icon: String) {
        getSharedPreferences(context).edit().putString(KEY_SELECTED_ICON, icon).apply()
    }

    fun getSelectedIcon(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_SELECTED_ICON, null)
    }
}
