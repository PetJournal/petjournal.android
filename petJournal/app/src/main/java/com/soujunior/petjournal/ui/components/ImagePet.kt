package com.soujunior.petjournal.ui.components

import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.soujunior.petjournal.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ImagePet(
    modifier: Modifier = Modifier,
    imagePath: String? = null,
    onImageChanged: (String) -> Unit = {},
) {
    val context = LocalContext.current
    var showSelectionDialog by remember { mutableStateOf(false) }
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ) { uri: Uri? ->
            uri?.let { onImageChanged(it.toString()) }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
        ) { success ->
            if (success && tempPhotoUri != null) {
                onImageChanged(tempPhotoUri.toString())
            }
        }

    fun createImageUri(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    if (showSelectionDialog) {
        AlertDialog(
            onDismissRequest = { showSelectionDialog = false },
            title = {
                Text(
                    text = "Selecionar Imagem",
                    style = MaterialTheme.typography.labelMedium,
                )
            },
            text = {
                Column {
                    ListItem(
                        headlineContent = { Text("Tirar Foto") },
                        leadingContent = { Icon(Icons.Default.CameraAlt, null) },
                        modifier =
                            Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    showSelectionDialog = false
                                    val uri = createImageUri()
                                    tempPhotoUri = uri
                                    cameraLauncher.launch(uri)
                                },
                        colors =
                            ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                leadingIconColor = MaterialTheme.colorScheme.onPrimary,
                                headlineColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ListItem(
                        headlineContent = { Text("Galeria") },
                        leadingContent = { Icon(Icons.Default.PhotoLibrary, null) },
                        modifier =
                            Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    showSelectionDialog = false
                                    galleryLauncher.launch("image/*")
                                },
                        colors =
                            ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                leadingIconColor = MaterialTheme.colorScheme.onPrimary,
                                headlineColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showSelectionDialog = false }) { Text("Cancelar") }
            },
        )
    }

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(Color.Transparent),
        horizontalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.weight(2f),
            horizontalArrangement = Arrangement.Center,
        ) {
            val shape = RoundedCornerShape(16.dp)
            val imageSize = 150.dp

            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier
                        .size(imageSize)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                            shape = shape,
                        )
                        .clip(shape)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .clickable { showSelectionDialog = true },
            ) {
                if (!imagePath.isNullOrEmpty()) {
                    GlideImage(
                        context = context,
                        url = imagePath,
                        scaleType = ImageView.ScaleType.CENTER_CROP,
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = "Adicionar Foto",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(32.dp),
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Adicionar",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                if (!imagePath.isNullOrEmpty()) {
                    Box(
                        modifier =
                            Modifier
                                .padding(8.dp)
                                .size(24.dp)
                                .align(Alignment.BottomEnd)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .clickable { showSelectionDialog = true },
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            modifier = Modifier.size(14.dp),
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "Editar",
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                }
            }
        }

//        Column(
//            modifier = Modifier.weight(1f),
//            horizontalAlignment = Alignment.End,
//        ) {
//            if (!imagePath.isNullOrEmpty()) {
//                Box(
//                    modifier =
//                        Modifier
//                            .clip(CircleShape)
//                            .padding(end = 24.sdp)
//                            .clickable { onImageChanged("") },
//                ) {
//                    Image(
//                        modifier = Modifier.size(24.dp),
//                        painter = painterResource(id = R.drawable.ic_delete),
//                        contentDescription = "Deletar",
//                        contentScale = ContentScale.FillBounds,
//                    )
//                }
//            }
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePetDialogPreview() {
    MaterialTheme {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Selecionar Imagem") },
            text = {
                Column {
                    ListItem(
                        headlineContent = { Text("Tirar Foto") },
                        leadingContent = { Icon(Icons.Default.CameraAlt, null) },
                        modifier = Modifier.clickable { },
                    )
                    ListItem(
                        headlineContent = { Text("Galeria") },
                        leadingContent = { Icon(Icons.Default.PhotoLibrary, null) },
                        modifier = Modifier.clickable { },
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { }) { Text("Cancelar") }
            },
        )
    }
}

@Preview(showBackground = true, name = "Estado: Sem Imagem")
@Composable
fun ImagePetEmptyPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            ImagePet(
                imagePath = null,
                onImageChanged = {},
            )
        }
    }
}

@Preview(showBackground = true, name = "Estado: Com Imagem (Placeholder)")
@Composable
fun ImagePetFilledPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            ImagePet(
                imagePath = "https://picsum.photos/200",
                onImageChanged = {},
            )
        }
    }
}
