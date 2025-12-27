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
    
    // Load image when path changes
    LaunchedEffect(backgroundImagePath) {
        backgroundBitmap = if (backgroundImagePath != null && backgroundImagePath.isNotEmpty()) {
            try {
                val file = File(backgroundImagePath)
                if (file.exists() && file.length() > 0) {
                    // Load bitmap in IO dispatcher
                    withContext(Dispatchers.IO) {
                        try {
                            BitmapFactory.decodeFile(backgroundImagePath)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }
                    }
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } else {
            null
        }
    }
    
    Box(modifier = modifier.fillMaxSize()) {
        // Background Image - draw behind content
        backgroundBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "背景图片",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        // Content - draw on top
        content()
    }
}
