package com.soujunior.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream

object ImageHelper {

    fun getFileFromUri(context: Context, uri: Uri): File? {
        val fileName = "pet_image_${System.currentTimeMillis()}.jpg"
        val tempFile = File(context.cacheDir, fileName)
        val copySuccess = try {
            val contentResolver = context.contentResolver
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(tempFile).use { outputStream -> 
                    inputStream.copyTo(outputStream) 
                }
            }
            true
        } catch (e: Exception) {
            Log.e("ImageHelper", "Erro ao copiar arquivo bruto", e)
            false
        }

        if (!copySuccess || !tempFile.exists()) {
            return null
        }

        processAndCompressLocalFile(context, tempFile)
        return tempFile
    }

    internal fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.outHeight to options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize >= reqHeight || halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    private fun processAndCompressLocalFile(context: Context, srcFile: File) {
        val maxDimension = 200
        val compressedFile = File(context.cacheDir, "comp_${System.currentTimeMillis()}.jpg")
        try {
            val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeFile(srcFile.absolutePath, options)
            val srcWidth = options.outWidth
            val srcHeight = options.outHeight
            if (srcWidth <= 0 || srcHeight <= 0) return

            val decodeOptions = BitmapFactory.Options().apply {
                inJustDecodeBounds = false
                inSampleSize = calculateInSampleSize(options, maxDimension, maxDimension)
                inPreferredConfig = Bitmap.Config.RGB_565
            }
            var bitmap = BitmapFactory.decodeFile(srcFile.absolutePath, decodeOptions) ?: return

            var rotation = 0
            try {
                val exifInterface = ExifInterface(srcFile.absolutePath)
                val orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                rotation = when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> 270
                    else -> 0
                }
            } catch (e: Exception) {
                Log.e("ImageHelper", "Erro ao obter EXIF", e)
            }

            if (rotation != 0) {
                val matrix = Matrix().apply { postRotate(rotation.toFloat()) }
                val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                if (rotatedBitmap != bitmap) {
                    bitmap.recycle()
                    bitmap = rotatedBitmap
                }
            }

            val currentWidth = bitmap.width
            val currentHeight = bitmap.height
            if (currentWidth > maxDimension || currentHeight > maxDimension) {
                val ratio = maxDimension.toFloat() / Math.max(currentWidth, currentHeight)
                val targetWidth = (currentWidth * ratio).toInt()
                val targetHeight = (currentHeight * ratio).toInt()
                val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
                if (scaledBitmap != bitmap) {
                    bitmap.recycle()
                    bitmap = scaledBitmap
                }
            }

            FileOutputStream(compressedFile).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            }
            bitmap.recycle()

            if (compressedFile.exists() && compressedFile.length() > 0) {
                if (srcFile.delete()) {
                    compressedFile.renameTo(srcFile)
                } else {
                    compressedFile.delete()
                }
            }
        } catch (e: Exception) {
            Log.e("ImageHelper", "Erro no processamento da imagem local", e)
            if (compressedFile.exists()) {
                compressedFile.delete()
            }
        }
    }
}
