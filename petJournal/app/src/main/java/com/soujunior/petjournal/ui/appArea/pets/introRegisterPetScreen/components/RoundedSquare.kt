<<<<<<<< Updated upstream:petJournal/app/src/main/java/com/soujunior/petjournal/ui/appArea/registerPetScreen/components/RoundedSquare.kt
package com.soujunior.petjournal.ui.appArea.registerPetScreen.components
========
package com.soujunior.petjournal.ui.appArea.pets.introRegisterPetScreen.components
>>>>>>>> Stashed changes:petJournal/app/src/main/java/com/soujunior/petjournal/ui/appArea/pets/introRegisterPetScreen/components/RoundedSquare.kt

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
@Composable
fun RoundedSquare(
    size: Dp,
    topLeftRadius: Dp,
    topRightRadius: Dp,
    bottomLeftRadius: Dp,
    bottomRightRadius: Dp,
    image: Painter,
    material: Color
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(
                color = material
                ,
                shape = RoundedCornerShape(
                    topStart = topLeftRadius,
                    topEnd = topRightRadius,
                    bottomStart = bottomLeftRadius,
                    bottomEnd = bottomRightRadius
                )
            )
    )
    {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(0.70f).align(Alignment.Center),
        )
    }
}