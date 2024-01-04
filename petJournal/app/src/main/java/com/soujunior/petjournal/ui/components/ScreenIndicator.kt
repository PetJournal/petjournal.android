package com.soujunior.petjournal.ui.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
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
fun ScreenIndicator(index: Int){

    val screens = listOf(
        stringResource(R.string.pet_registration),
        stringResource(R.string.pet_races),
        stringResource(R.string.pet_size),
        stringResource(R.string.pet_birth)
    )

    // Cadastro pet

    val text = if(index in screens.indices){
        val concatenatedString = buildString(screens = screens, index = index)
        concatenatedString
    }
    else{
        Log.e("Error", "INDICE FORA DO INTERVALO/INDEX OUT OF RANGE")
        ""
    }

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text

    )
}

@Composable
private fun buildString(screens: List<String>, index: Int): String{
    if(index == 0){
        return screens[0]
    }
    else{
        val screenText = screens.subList(0, index+1)
        return screenText.joinToString(" > ")
    }
}

@Preview
@Composable
private fun PreviewScreenIndicator(){
    ScreenIndicator(index = 0)

}