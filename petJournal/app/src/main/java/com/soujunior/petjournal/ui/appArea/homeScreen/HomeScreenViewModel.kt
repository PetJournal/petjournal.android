package com.soujunior.petjournal.ui.appArea.homeScreen

import androidx.lifecycle.ViewModel

abstract class HomeScreenViewModel : ViewModel() {
    /*TODO: (Gelson) 13: Aqui, vamos fazer o seguinte, acredito que a gente já consiga ter uma noção
       de como é criado um esquema/modelo UML.
       Tente imaginar como irá funcionar a tela por meio de um desenho esquemático feito por você.
       Terão dois momentos em que a tela home será chamada posteriormente, mas no momento, vamos focar somente na tela de login:
       A partir do momento que o login for feito, o usuário ira receber uma chave jwt para autenticações futuras.
       Quando o usuário for redirecionado para a tela home, a tela irá realizar os seguintes processos pelo que consigo imaginar no momento:
       - ira solicitar para a API o nome do usuário (getUserName).
       - esse nome será salvo na camada de dados no Room, imagino.
       - o resultado do Nome que vamos obter será diretamente do Room, e não da api. Como assim?:
       quando a solicitação do nome for feita para a camada de dados, vamos verificar em uma base de dados se esse nome já existe, se não existir, será feita a solicitação para a API.
        - esse nome vai ser emcaminhado para o layout onde será usado.
        Basicamente isso.
       */
}