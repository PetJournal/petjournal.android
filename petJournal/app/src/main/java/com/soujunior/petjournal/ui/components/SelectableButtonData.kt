package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.model.SelectableButtonInfo
import com.soujunior.petjournal.ui.model.TagAction
import com.soujunior.petjournal.ui.theme.ColorCustom
import com.soujunior.petjournal.ui.util.shimmerEffect
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ManageTagsDialog(
    tags: List<SelectableButtonInfo>,
    onDismiss: () -> Unit,
    onAction: (TagAction) -> Unit,
) {
    var isFormScreen by remember { mutableStateOf(false) }
    var editingTag by remember { mutableStateOf<SelectableButtonInfo?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.manager_tags),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                if (isFormScreen) {
                    TagForm(
                        tagToEdit = editingTag,
                        onSave = { name, color ->
                            val id = editingTag?.id
                            if (id != null) {
                                onAction(TagAction.Update(id, name, color))
                            } else {
                                onAction(TagAction.Create(name, color))
                            }
                            isFormScreen = false
                            editingTag = null
                        },
                        onCancel = {
                            isFormScreen = false
                            editingTag = null
                        },
                    )
                } else {
                    TagList(
                        tags = tags,
                        onEdit = { tag ->
                            editingTag = tag
                            isFormScreen = true
                        },
                        onDelete = { tag ->
                            tag.id?.let { onAction(TagAction.Delete(it)) }
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { isFormScreen = true },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Criar Nova Tag")
                    }
                }
            }
        }
    }
}

@Composable
fun TagList(
    tags: List<SelectableButtonInfo>,
    onEdit: (SelectableButtonInfo) -> Unit,
    onDelete: (SelectableButtonInfo) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight(0.5f),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(tags.size) { index ->
            val tag = tags[index]
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier =
                            Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(tag.color),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = tag.title, style = MaterialTheme.typography.bodyMedium)
                }
                Row {
                    IconButton(onClick = { onEdit(tag) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                        )
                    }
                    IconButton(onClick = { onDelete(tag) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TagForm(
    tagToEdit: SelectableButtonInfo?,
    onSave: (String, Color) -> Unit,
    onCancel: () -> Unit,
) {
    var name by remember { mutableStateOf(tagToEdit?.title ?: "") }
    var selectedColor by remember { mutableStateOf(tagToEdit?.color ?: Color(0xFFE57373)) }

    val palette =
        listOf(
            Color(0xFFE57373), Color(0xFFF06292), Color(0xFFBA68C8),
            Color(0xFF9575CD), Color(0xFF7986CB), Color(0xFF64B5F6),
            Color(0xFF4FC3F7), Color(0xFF4DD0E1), Color(0xFF4DB6AC),
            Color(0xFF81C784), Color(0xFFAED581), Color(0xFFFF8A65),
        )

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.tag_name)) },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.tags_colors), style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(palette.size) { index ->
                val color = palette[index]
                Box(
                    modifier =
                        Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(color)
                            .clickable { selectedColor = color }
                            .then(
                                if (selectedColor == color) {
                                    Modifier.border(
                                        2.dp,
                                        MaterialTheme.colorScheme.onSurface,
                                        CircleShape,
                                    )
                                } else {
                                    Modifier
                                },
                            ),
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(onClick = onCancel) {
                Text("Cancelar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onSave(name, selectedColor)
                    }
                },
                enabled = name.isNotBlank(),
            ) {
                Text("Salvar")
            }
        }
    }
}

@Composable
fun SelectableButton(
    modifier: Modifier = Modifier,
    titleButton: String,
    colorButton: Color,
    isSelected: Boolean,
    isLoading: Boolean = false,
    onSelectionChanged: (String, Boolean) -> Unit,
) {
    Button(
        modifier =
            modifier
                .height(40.dp)
                .then(
                    if (isLoading) {
                        Modifier
                            .clip(RoundedCornerShape(size = 16.dp))
                            .shimmerEffect()
                    } else if (isSelected) {
                        Modifier.shadow(
                            elevation = 10.dp,
                            spotColor = ColorCustom.shadow_color_selectable_button,
                            ambientColor = ColorCustom.shadow_color_selectable_button,
                        )
                    } else {
                        Modifier
                    },
                ),
        enabled = !isLoading,
        onClick = {
            onSelectionChanged(titleButton, !isSelected)
        },
        colors =
            ButtonDefaults.buttonColors(
                containerColor =
                    if (isLoading) {
                        Color.Transparent
                    } else if (isSelected) {
                        colorButton
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                contentColor =
                    if (isLoading) {
                        Color.Transparent
                    } else if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        colorButton
                    },
                disabledContainerColor = if (isLoading) Color.Transparent else Color.Unspecified,
                disabledContentColor = if (isLoading) Color.Transparent else Color.Unspecified,
            ),
        shape = RoundedCornerShape(size = 16.dp),
        border =
            if (!isSelected && !isLoading) {
                BorderStroke(
                    1.dp,
                    ColorCustom.border_color_selectable_button,
                )
            } else {
                null
            },
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 2.dp),
    ) {
        Text(
            text = titleButton,
            style =
                MaterialTheme.typography.labelMedium.copy(
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color =
                        if (isLoading) {
                            Color.Transparent
                        } else if (isSelected) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            colorButton
                        },
                    textAlign = TextAlign.Center,
                ),
        )
    }
}

@Composable
fun GroupSelectableButton(
    modifier: Modifier = Modifier,
    listOfTags: List<SelectableButtonInfo>,
    selectedTag: String? = null,
    isLoading: Boolean = false,
    showButton: Boolean = false,
    onAddClick: () -> Unit = {},
    onSelection: (String?) -> Unit = {},
    onAction: (TagAction) -> Unit = {},
    maxItemsInEachRow: Int = Int.MAX_VALUE,
) {
    val displayTasks =
        if (isLoading && listOfTags.isEmpty()) {
            List(6) { SelectableButtonInfo(null, "", Color.Transparent) }
        } else {
            listOfTags
        }

    var showManageTagsDialog by remember { mutableStateOf(false) }

    if (showManageTagsDialog) {
        ManageTagsDialog(
            tags = listOfTags,
            onDismiss = { showManageTagsDialog = false },
            onAction = onAction,
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.label_select_main_category),
                style = MaterialTheme.typography.titleMedium,
                color = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight(500),
                modifier =
                    if (isLoading) {
                        Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect()
                    } else {
                        Modifier
                    },
            )
            if (showButton == true && isLoading == false) {
                Surface(
                    modifier =
                        Modifier
                            .clip(CircleShape)
                            .size(24.sdp)
                            .clickable {
                                onAddClick()
                                showManageTagsDialog = true
                            },
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.addpet),
                            tint = Color.White,
                            modifier = Modifier.size(16.sdp),
                        )
                    }
                }
            }
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            maxItemsInEachRow = if (isLoading) 3 else maxItemsInEachRow,
        ) {
            displayTasks.forEach { buttonInfo ->
                SelectableButton(
                    titleButton = buttonInfo.title,
                    colorButton = buttonInfo.color,
                    isSelected = buttonInfo.id == selectedTag,
                    isLoading = isLoading,
                    onSelectionChanged = { title, selected ->
                        if (selected) {
                            onSelection(buttonInfo.id)
                        } else {
                            onSelection("")
                        }
                    },
                    modifier =
                        if (isLoading) {
                            Modifier
                                .weight(1f)
                                .padding(bottom = 15.dp)
                        } else {
                            Modifier
                                .defaultMinSize(minWidth = 72.dp)
                                .padding(bottom = 15.dp)
                        },
                )
            }
        }
    }
}
