package com.soujunior.petjournal.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

@Composable
fun Button3(
    submit: () -> Unit,
    enableButton: Boolean,
    modifier: Modifier = Modifier,
    text: String = "Button",
    textSize: TextUnit = 12.ssp,
    contentPaddingValues: Dp = 12.sdp,
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
    textColor: Color = MaterialTheme.colorScheme.primary,
    isLoading: Boolean = false,
    borderColor: Color = MaterialTheme.colorScheme.onBackground,
    shape: Shape = RoundedCornerShape(size = 50.dp),
    icon: @Composable (() -> Unit)? = null,
) {
    val coroutineScope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }

    androidx.compose.material3.Button(
        onClick = { submit() },
        enabled = enableButton,
        modifier =
            modifier
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                }
                .pointerInput(enableButton) {
                    if (enableButton) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitFirstDown(requireUnconsumed = false)
                                coroutineScope.launch {
                                    scale.animateTo(0.90f, animationSpec = tween(100))
                                }
                                waitForUpOrCancellation()
                                coroutineScope.launch {
                                    scale.animateTo(1f, animationSpec = tween(100))
                                }
                            }
                        }
                    }
                }
                .width(120.sdp)
                .shadow(
                    elevation = 15.dp,
                    spotColor = MaterialTheme.colorScheme.onBackground,
                    ambientColor = MaterialTheme.colorScheme.onBackground,
                    shape = shape,
                ),
        border =
            BorderStroke(
                width = 1.sdp,
                color = borderColor,
            ),
        shape = shape,
        colors = buttonColor,
        contentPadding = PaddingValues(contentPaddingValues),
    ) {
        if (!isLoading) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                if (icon != null) {
                    icon()
                    Spacer(modifier = Modifier.width(8.sdp))
                }
                Text(
                    text = text,
                    fontWeight = FontWeight.W500,
                    fontSize = textSize,
                    style = MaterialTheme.typography.headlineLarge,
                    color = textColor,
                )
            }
        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(17.sdp),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
fun LogoutButton(
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    Button3(
        submit = onLogoutClick,
        enableButton = true,
        text = "Logout",
        textColor = primaryColor,
        borderColor = primaryColor,
        shape = RoundedCornerShape(size = 20.dp),
        buttonColor = ButtonDefaults.buttonColors(containerColor = Color.White),
        icon = {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Logout,
                contentDescription = null,
                tint = primaryColor,
                modifier = Modifier.size(20.sdp),
            )
        },
        modifier =
            modifier
                .padding(vertical = 20.sdp)
                .width(250.sdp),
    )
}

@Preview(showBackground = true)
@Composable
fun LogoutButtonPreview() {
    MaterialTheme {
        LogoutButton(onLogoutClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun Button3OriginalBehaviorPreview() {
    Button3(submit = { }, enableButton = true, text = "Salvar", modifier = Modifier.padding(vertical = 20.sdp).fillMaxWidth())
}
