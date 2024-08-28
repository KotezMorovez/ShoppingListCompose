package com.example.shoppinglistcompose.data.service

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.net.toUri
import com.example.shoppinglistcompose.domain.model.Item
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject


interface CacheService {
    fun addImageToCache(uri: Uri): Uri?
}

class CacheServiceImpl @Inject constructor(
    private val context: Context
) : CacheService {
    override fun addImageToCache(uri: Uri): Uri? {
        val bitmap = uriToBitmap(uri) ?: return null

        return createCacheFile(bitmap)
    }

    private fun createCacheFile(bitmap: Bitmap): Uri {
        val root = context.cacheDir.absolutePath + "/shopping_list_image_cache"
        val cacheUri = root + "/" + UUID.randomUUID() + ".png"
        val dir = File(root)
        dir.mkdir()

        FileOutputStream(cacheUri).use { out ->
            bitmap.compress(
                Bitmap.CompressFormat.PNG,
                100,
                out
            )
        }

        return cacheUri.toUri()
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        var bitmap: Bitmap? = null
        val contentResolver: ContentResolver = context.contentResolver
        try {
            bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
}