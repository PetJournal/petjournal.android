package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.ImageLogo
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.util.capitalizeFirstLetter

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Screen(navController: NavController){

    Column(modifier = Modifier.navigationBarsPadding()){
        ScaffoldCustom(
            modifier = Modifier,
            navigationUp = navController,
            showTopBar = true,
            showBottomBarNavigation = false,
            contentToUse = {
                Box(modifier = Modifier.padding(it)){
//                    if(taskState is TaskState.Loading){}

                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        content = {
                            item {
                                Header(species = "Gato")
                            }
                            item {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp, 0.dp),
                                    text = stringResource(id = R.string.pet_name),
                                    style = MaterialTheme.typography.displayMedium,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal)
                            }
                            item {
                                InputText(
                                    textTop = "Nome: ",
                                    textHint = "Digite aqui...",
                                    textValue = "Digite Aqui....",
                                    onEvent = {})
                            }
                            item{
                                GenderSelector()
                            }
                            item {
                                Row {
                                    Button2(submit = { /*TODO*/ }, enableButton = true)
                                    Button2(submit = { /*TODO*/ }, enableButton = true)
                                }
                            }
                        })

                }
            })
    }

}


@Preview
@Composable
private fun PreviewGenderScreen(){
    Screen(navController = rememberNavController())
}