package com.soujunior.petjournal.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R

/**
 * Este metodo é responsável por exibir um pequeno "roadmap" de qual tela o usuário se encontra
 *
 * @param Index representa qual etapa do Cadastro Pet, o usuário se encontra, exibindo um dos seguintes textos.
 *
 * 0 = Cadastro Pet
 * 1 = Cadastro Pet > Raças
 * 2 = Cadastro Pet > Raças > Porte
 * 3 = Cadastro Pet > Raças > Porte > Nascimento
 * */
@Composable
fun Breadcrumb(index: Int){

    val screens = listOf(
        stringResource(R.string.pet_registration),
        stringResource(R.string.pet_name_gender),
        stringResource(R.string.pet_size),
        stringResource(R.string.pet_birth)
    )

    val text: AnnotatedString = if(index in screens.indices){
        val concatenatedString = buildString(screens = screens, index = index)
        concatenatedString
    }else{
        Log.e("Error", "INDEX OUT OF RANGE")
        buildAnnotatedString {  }
    }


    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(2.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Home Icon",
                tint = MaterialTheme.colorScheme.primary)
        }
        Column(modifier = Modifier.padding(2.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
            )
        }
    }
}

@Composable
private fun buildString(screens: List<String>, index: Int): AnnotatedString{
    val screenText = screens.subList(0, index+1)
    return buildAnnotatedString {
        screenText.forEachIndexed { index, screen ->
            val isLast = index == screenText.size-1
            withStyle(
                style = SpanStyle(
                    fontSize = 10.sp,
                    fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                    letterSpacing = MaterialTheme.typography.headlineMedium.letterSpacing,
                    color =
                if(isLast) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onBackground)
            ){
                append(screen)
            }
            if(!isLast) {
                withStyle(style = SpanStyle(
                    fontSize = 10.sp,
                    fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                    letterSpacing = MaterialTheme.typography.headlineMedium.letterSpacing,
                    color = MaterialTheme.colorScheme.onBackground
                )) {
                    append(" > ")
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewScreenIndicator(){
    Breadcrumb(index = 1)
}