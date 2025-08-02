package com.soujunior.petjournal.ui.screens_app.screens_pets.registerTaskScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.GroupSelectableButton
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.components.PetFilterList
import com.soujunior.petjournal.ui.components.Pets
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.TextFieldCustom
import com.soujunior.petjournal.ui.components.TransactionTypeSelector
import com.soujunior.petjournal.ui.util.TransactionType

@Composable
fun ScreenRegisterTask(navController: NavController) {

    val listPet = listOf(
        Pets(
            id = 1,
            imageRes = painterResource(R.drawable.image_jujuba),
            name = "Jujuba"
        ),
        Pets(
            id = 2,
            imageRes = painterResource(R.drawable.image_alfredo),
            name = "Alfredo"
        )
    )

    val nameTask = remember { mutableStateOf("") }
    val desc = remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<TransactionType?>(null) }

    Column(modifier = Modifier) {
        ScaffoldCustom(
            modifier = Modifier,
            navigationUp = navController,
            showTopBar = true,
            titleTopBar = stringResource(R.string.label_new_task),
            showBottomBarNavigation = true,
            contentToUse = {
                LazyColumn(
                    contentPadding = it,
                    content = {
                        item {
                            GroupSelectableButton()
                        }
                        item {
                            InputText(
                                modifier = Modifier,
                                placeholderText = "Digite aqui o nome da tarefa",
                                titleText = "Nome da tarefa",
                                textValue = nameTask.value,
                                onEvent = { t ->
                                    nameTask.value = t
                                },
                            )
                        }
                        item {
                            TextFieldCustom(
                                title = stringResource(R.string.label_description),
                                placeholder = stringResource(R.string.enter_the_task_description_here),
                                value = desc.value,
                                onValueChange = { d ->
                                    desc.value = d
                                }
                            )
                        }
                        item {
                            PetFilterList(
                                listPet,
                                onSelectedPet = {}
                            )
                        }
                        item {
                            Column {
                                TransactionTypeSelector(
                                    onSelectionChanged = { type ->
                                        selectedType = type
                                    }
                                )

                                when (selectedType) {
                                    TransactionType.Recurrent -> Text(text = "Recorrente")
                                    TransactionType.OneOff -> Text(text = "Pontual")
                                    null -> {}
                                }
                            }
                        }
                        item {
                            TextFieldCustom(
                                title = "Observação",
                                placeholder = "Digite aqui a sua observação",
                                value = desc.value,
                                onValueChange = { d ->
                                    desc.value = d
                                }
                            )
                        }
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_4_xl")
@Composable
fun ScreenRegisterTaskPreview() {
    val nav = rememberNavController()
    ScreenRegisterTask(nav)
}