// src/main/java/com/example/recording_app/ui/components/BackgroundImage.kt
package com.example.recording_app.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun BackgroundImageBox(
    backgroundImagePath: String?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var backgroundBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }

    // 🔧 修复: 添加更详细的错误处理和日志
    LaunchedEffect(backgroundImagePath) {
        backgroundBitmap = if (!backgroundImagePath.isNullOrEmpty()) {
            try {
                val file = File(backgroundImagePath)
                android.util.Log.d("BackgroundImage", "Loading image from: $backgroundImagePath")
                android.util.Log.d("BackgroundImage", "File exists: ${file.exists()}, size: ${file.length()}")

                if (file.exists() && file.length() > 0) {
                    withContext(Dispatchers.IO) {
                        try {
                            val options = BitmapFactory.Options().apply {
                                // 🔧 先获取图片尺寸,避免OOM
                                inJustDecodeBounds = true
                            }
                            BitmapFactory.decodeFile(backgroundImagePath, options)

                            // 🔧 如果图片太大,进行缩放
                            options.inJustDecodeBounds = false
                            options.inSampleSize = calculateInSampleSize(options, 1080, 1920)

                            val bitmap = BitmapFactory.decodeFile(backgroundImagePath, options)
                            android.util.Log.d("BackgroundImage", "Bitmap loaded: ${bitmap != null}")
                            bitmap
                        } catch (e: Exception) {
                            android.util.Log.e("BackgroundImage", "Error decoding bitmap", e)
                            null
                        }
                    }
                } else {
                    android.util.Log.w("BackgroundImage", "File not found or empty")
                    null
                }
            } catch (e: Exception) {
                android.util.Log.e("BackgroundImage", "Error loading image", e)
                null
            }
        } else {
            null
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Background Image
        backgroundBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "背景图片",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.9f // 🔧 添加透明度,避免内容看不清
            )
        }
        // Content on top
        content()
    }
}

// 🔧 添加图片缩放计算函数
private fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int,
    reqHeight: Int
): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        while (halfHeight / inSampleSize >= reqHeight &&
            halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}