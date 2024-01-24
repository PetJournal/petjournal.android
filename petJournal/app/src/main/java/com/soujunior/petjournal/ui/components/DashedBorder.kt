package com.soujunior.petjournal.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.dashedBorder(
    shape: Shape,
    isError: Boolean,
    isSelected: Boolean,
    selectedColor: Color = Color.Black,
    strokeWidth: Dp = 1.5.dp,
    dashWidth: Dp = 8.dp,
    gapWidth: Dp = 10.dp,
    cap: StrokeCap = StrokeCap.Square,
    join: StrokeJoin = StrokeJoin.Round
) = this.drawWithContent{
    val outline = shape.createOutline(size, layoutDirection, this)
    val path = Path()
    val color = if (isError)
            Color(0xFFFF917A)
        else if(isSelected)
            selectedColor
        else Color.Gray



    path.addOutline(outline)

    val stroke =
        if(isError || isSelected){
            Stroke(
                cap = cap,
                join = join,
                width = (strokeWidth*2).toPx(),
            )
        } else {
        Stroke(
            cap = cap,
            join = join,
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(dashWidth.toPx(), gapWidth.toPx(), 0f)
            )
        )
    }

    drawPath(
        path = path,
        style = stroke,
        color = color
    )

    drawContent()
}

fun Modifier.dashedBorder(width: Dp, radius: Dp, color: Color) =
    drawBehind {
        drawIntoCanvas {
            val paint = Paint()
                .apply {
                    strokeWidth = width.toPx()
                    this.color = color
                    style = PaintingStyle.Stroke
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                }
            it.drawRoundRect(
                width.toPx(),
                width.toPx(),
                size.width - width.toPx(),
                size.height - width.toPx(),
                radius.toPx(),
                radius.toPx(),
                paint
            )
            it.drawRoundRect(
                (width.toPx() / 2),
                (width.toPx() / 2),
                size.width - (width.toPx() / 2),
                size.height - (width.toPx() / 2),
                radius.toPx(),
                radius.toPx(),
                paint )
        }
    }

