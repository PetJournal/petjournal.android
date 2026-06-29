package com.soujunior.data.repository

import android.graphics.BitmapFactory
import com.soujunior.data.util.ImageHelper
import org.junit.Assert.assertEquals
import org.junit.Test

class ImageHelperTest {

    @Test
    fun `calculateInSampleSize when image is smaller than target`() {
        val options = BitmapFactory.Options().apply {
            outWidth = 150
            outHeight = 150
        }
        val sampleSize = ImageHelper.calculateInSampleSize(options, 200, 200)
        assertEquals(1, sampleSize)
    }

    @Test
    fun `calculateInSampleSize when image is larger than target`() {
        val options = BitmapFactory.Options().apply {
            outWidth = 800
            outHeight = 600
        }
        val sampleSize = ImageHelper.calculateInSampleSize(options, 200, 200)
        assertEquals(4, sampleSize)
    }

    @Test
    fun `calculateInSampleSize when image is extremely large`() {
        val options = BitmapFactory.Options().apply {
            outWidth = 4000
            outHeight = 3000
        }
        val sampleSize = ImageHelper.calculateInSampleSize(options, 200, 200)
        assertEquals(16, sampleSize)
    }
}
