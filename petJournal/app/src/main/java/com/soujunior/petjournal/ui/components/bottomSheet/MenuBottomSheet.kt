package com.soujunior.petjournal.ui.components.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.sharp.Apps
import androidx.compose.material.icons.sharp.Pets
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.horizontalButtonList.GridButtonList
import com.soujunior.petjournal.ui.components.horizontalButtonList.TagOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val isPreview = LocalInspectionMode.current

    if (isVisible) {
        if (isPreview) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onDismiss,
                        ),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Surface(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable(enabled = false) {},
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp,
                ) {
                    Column(modifier = Modifier.padding(bottom = 0.dp)) {
                        Box(
                            modifier =
                                Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 12.dp)
                                    .width(32.dp)
                                    .height(4.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                                        shape = RoundedCornerShape(2.dp),
                                    ),
                        )
                        content()
                    }
                }
            }
        } else {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            val scope = rememberCoroutineScope()

            ModalBottomSheet(
                onDismissRequest = onDismiss,
                sheetState = sheetState,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                containerColor = MaterialTheme.colorScheme.surface,
                dragHandle = { BottomSheetDefaults.DragHandle() },
            ) {
                content()
            }
        }
    }
}

@Composable
fun CategoryMenu(onSelect: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.category),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                val items =
                    listOf(
                        TagOption("1", "Todos", Icons.Sharp.Apps, MaterialTheme.colorScheme.primary),
                        TagOption("2", "Vacinas", Icons.Sharp.Pets, MaterialTheme.colorScheme.secondary),
                        TagOption("3", "Banho", Icons.Sharp.Apps, MaterialTheme.colorScheme.tertiary),
                        TagOption("4", "Consulta", Icons.Sharp.Pets, MaterialTheme.colorScheme.error),
                        TagOption("5", "Exames", Icons.Sharp.Apps, MaterialTheme.colorScheme.primary),
                        TagOption("5", "Exames", Icons.Sharp.Apps, MaterialTheme.colorScheme.primary),
                        TagOption("5", "Exames", Icons.Sharp.Apps, MaterialTheme.colorScheme.primary),
                        TagOption("5", "Exames", Icons.Sharp.Apps, MaterialTheme.colorScheme.primary),
                        TagOption("5", "Exames", Icons.Sharp.Apps, MaterialTheme.colorScheme.primary),
                        TagOption("5", "Exames", Icons.Sharp.Apps, MaterialTheme.colorScheme.primary),
                        TagOption("5", "Exames", Icons.Sharp.Apps, MaterialTheme.colorScheme.primary),
                        TagOption("6", "Hotel", Icons.Sharp.Pets, MaterialTheme.colorScheme.secondary),
                    )

                GridButtonList(
                    menuItems = items,
                    quantityColumn = 3,
                    onItemClick = { tagOption ->
                        onSelect(tagOption.label)
                    },
                )

                // CORREÇÃO: Espaçamento de segurança fixo.
                // 64dp garante que o conteúdo suba acima da barra de navegação
                // independentemente da configuração de insets do sistema.
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

@Preview(
    name = "Edit Mode (Static)",
    showBackground = true,
    device = "spec:width=411dp,height=891dp",
)
@Composable
private fun PreviewMenuBottomSheetStatic() {
    MaterialTheme {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color.White),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("Fundo da tela...")
                Button(onClick = {}) { Text("Botão") }
            }

            MenuBottomSheet(
                isVisible = true,
                onDismiss = {},
            ) {
                CategoryMenu(onSelect = {})
            }
        }
    }
}

@Composable
private fun InteractiveScreenSample() {
    var isOpen by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { isOpen = true },
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text("Abrir Menu") },
                containerColor = Color(0xFF00C853),
                contentColor = Color.White,
            )
        },
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                "Modo Interativo: ATIVADO",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        MenuBottomSheet(
            isVisible = isOpen,
            onDismiss = { isOpen = false },
        ) {
            CategoryMenu(
                onSelect = { action ->
                    println("Selecionado: $action")
                    isOpen = false
                },
            )
        }
    }
}

@Preview(showBackground = true, name = "Interactive Mode")
@Composable
private fun PreviewMenuBottomSheetInteractive() {
    MaterialTheme {
        InteractiveScreenSample()
    }
}

// package com.soujunior.petjournal.ui.components.bottomSheet
//
// import androidx.compose.foundation.background
// import androidx.compose.foundation.clickable
// import androidx.compose.foundation.interaction.MutableInteractionSource
// import androidx.compose.foundation.layout.Arrangement
// import androidx.compose.foundation.layout.Box
// import androidx.compose.foundation.layout.Column
// import androidx.compose.foundation.layout.ColumnScope
// import androidx.compose.foundation.layout.fillMaxSize
// import androidx.compose.foundation.layout.fillMaxWidth
// import androidx.compose.foundation.layout.height
// import androidx.compose.foundation.layout.navigationBarsPadding
// import androidx.compose.foundation.layout.padding
// import androidx.compose.foundation.layout.width
// import androidx.compose.foundation.shape.RoundedCornerShape
// import androidx.compose.material.icons.Icons
// import androidx.compose.material.icons.filled.Add
// import androidx.compose.material.icons.sharp.Apps
// import androidx.compose.material.icons.sharp.Pets
// import androidx.compose.material3.BottomSheetDefaults
// import androidx.compose.material3.Button
// import androidx.compose.material3.ExperimentalMaterial3Api
// import androidx.compose.material3.ExtendedFloatingActionButton
// import androidx.compose.material3.HorizontalDivider
// import androidx.compose.material3.Icon
// import androidx.compose.material3.MaterialTheme
// import androidx.compose.material3.ModalBottomSheet
// import androidx.compose.material3.Scaffold
// import androidx.compose.material3.Surface
// import androidx.compose.material3.Text
// import androidx.compose.material3.rememberModalBottomSheetState
// import androidx.compose.runtime.Composable
// import androidx.compose.runtime.getValue
// import androidx.compose.runtime.mutableStateOf
// import androidx.compose.runtime.remember
// import androidx.compose.runtime.rememberCoroutineScope
// import androidx.compose.runtime.setValue
// import androidx.compose.ui.Alignment
// import androidx.compose.ui.Modifier
// import androidx.compose.ui.graphics.Color
// import androidx.compose.ui.platform.LocalInspectionMode
// import androidx.compose.ui.res.stringResource
// import androidx.compose.ui.text.style.TextAlign
// import androidx.compose.ui.tooling.preview.Preview
// import androidx.compose.ui.unit.dp
// import androidx.compose.ui.unit.sp
// import com.soujunior.petjournal.R
// import com.soujunior.petjournal.ui.components.horizontalButtonList.GridButtonList
// import com.soujunior.petjournal.ui.components.horizontalButtonList.TagOption
//
// @OptIn(ExperimentalMaterial3Api::class)
// @Composable
// fun MenuBottomSheet(
//    isVisible: Boolean,
//    onDismiss: () -> Unit,
//    content: @Composable ColumnScope.() -> Unit,
// ) {
//    val isPreview = LocalInspectionMode.current
//
//    if (isVisible) {
//        if (isPreview) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Black.copy(alpha = 0.5f))
//                    .clickable(
//                        interactionSource = remember { MutableInteractionSource() },
//                        indication = null,
//                        onClick = onDismiss,
//                    ),
//                contentAlignment = Alignment.BottomCenter,
//            ) {
//                Surface(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable(enabled = false) {},
//                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
//                    color = MaterialTheme.colorScheme.surface,
//                    tonalElevation = 6.dp,
//                ) {
//                    Column(modifier = Modifier.padding(bottom = 0.dp)) {
//                        Box(
//                            modifier = Modifier
//                                .align(Alignment.CenterHorizontally)
//                                .padding(vertical = 12.dp)
//                                .width(32.dp)
//                                .height(4.dp)
//                                .background(
//                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
//                                    shape = RoundedCornerShape(2.dp),
//                                ),
//                        )
//                        content()
//                    }
//                }
//            }
//        } else {
//            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
//            val scope = rememberCoroutineScope()
//
//            ModalBottomSheet(
//                onDismissRequest = onDismiss,
//                sheetState = sheetState,
//                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
//                containerColor = MaterialTheme.colorScheme.surface,
//                dragHandle = { BottomSheetDefaults.DragHandle() },
//            ) {
//                content()
//            }
//        }
//    }
// }
//
// @Composable
// fun CategoryMenu(onSelect: (String) -> Unit) {
//    val isPreview = LocalInspectionMode.current
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .then(
//                if (isPreview) {
//                    Modifier.padding(bottom = 24.dp)
//                } else {
//                    Modifier.navigationBarsPadding()
//                },
//            ),
//    ) {
//        Box(
//            modifier = Modifier.fillMaxWidth(),
//            contentAlignment = Alignment.Center,
//        ) {
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) {
//                Text(
//                    text = stringResource(R.string.category),
//                    style = MaterialTheme.typography.titleLarge,
//                    fontSize = 20.sp,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 24.dp, vertical = 12.dp),
//                )
//
//                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
//
//                val items = listOf(
//                    TagOption("1", "Todos", Icons.Sharp.Apps, MaterialTheme.colorScheme.primary),
//                    TagOption("2", "Vacinas", Icons.Sharp.Pets, MaterialTheme.colorScheme.secondary),
//                    TagOption("3", "Banho", Icons.Sharp.Apps, MaterialTheme.colorScheme.tertiary),
//                    TagOption("4", "Consulta", Icons.Sharp.Pets, MaterialTheme.colorScheme.error),
//                    TagOption("5", "Exames", Icons.Sharp.Apps, MaterialTheme.colorScheme.primary),
//                    TagOption("6", "Hotel", Icons.Sharp.Pets, MaterialTheme.colorScheme.secondary),
//                )
//
//                GridButtonList(
//                    menuItems = items,
//                    quantityColumn = 3,
//                    onItemClick = { tagOption ->
//                        onSelect(tagOption.label)
//                    }
//                )
//            }
//        }
//    }
// }
//
// @Preview(
//    name = "Edit Mode (Static)", showBackground = true,
//    device = "spec:width=411dp,height=891dp",
// )
// @Composable
// private fun PreviewMenuBottomSheetStatic() {
//    MaterialTheme {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White),
//        ) {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center,
//            ) {
//                Text("Fundo da tela...")
//                Button(onClick = {}) { Text("Botão") }
//            }
//
//            MenuBottomSheet(
//                isVisible = true,
//                onDismiss = {},
//            ) {
//                CategoryMenu(onSelect = {})
//            }
//        }
//    }
// }
//
// @Composable
// private fun InteractiveScreenSample() {
//    var isOpen by remember { mutableStateOf(false) }
//
//    Scaffold(
//        floatingActionButton = {
//            ExtendedFloatingActionButton(
//                onClick = { isOpen = true },
//                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
//                text = { Text("Abrir Menu") },
//                containerColor = Color(0xFF00C853),
//                contentColor = Color.White,
//            )
//        },
//    ) { padding ->
//        Box(
//            modifier = Modifier
//                .padding(padding)
//                .fillMaxSize(),
//            contentAlignment = Alignment.Center,
//        ) {
//            Text(
//                "Modo Interativo: ATIVADO",
//                style = MaterialTheme.typography.headlineSmall,
//                color = MaterialTheme.colorScheme.primary,
//            )
//        }
//
//        MenuBottomSheet(
//            isVisible = isOpen,
//            onDismiss = { isOpen = false },
//        ) {
//            CategoryMenu(
//                onSelect = { action ->
//                    println("Selecionado: $action")
//                    isOpen = false
//                },
//            )
//        }
//    }
// }
//
// @Preview(showBackground = true, name = "Interactive Mode")
// @Composable
// private fun PreviewMenuBottomSheetInteractive() {
//    MaterialTheme {
//        InteractiveScreenSample()
//    }
// }
