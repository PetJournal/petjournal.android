package com.soujunior.petjournal.ui.components.data

import androidx.compose.ui.graphics.Color
import com.soujunior.petjournal.R

object TaskTypes {
    val VACINA =
        TaskType(
            id = "vacina",
            name = "Vacina",
            color = Color(0xFFFA680E),
            icon = R.drawable.icone_vacinas,
            iconVector = R.drawable.icone_vacinas_vector,
        )

    val CONSULTAS =
        TaskType(
            id = "consultas",
            name = "Consultas",
            color = Color(0xFF20955E),
            icon = R.drawable.icone_consulta,
            iconVector = R.drawable.icone_consultas_vector,
        )

    val RACAO =
        TaskType(
            id = "racao",
            name = "Ração",
            color = Color(0xFF881803),
            icon = R.drawable.icone_racao,
            iconVector = R.drawable.icone_racao_vector,
        )

    val MEDICAMENTOS =
        TaskType(
            id = "medicamentos",
            name = "Medicamentos",
            color = Color(0xFF2F99E5),
            icon = R.drawable.icone_medicamento,
            iconVector = R.drawable.icone_medicamentos_vector,
        )

    val BANHOS =
        TaskType(
            id = "banhos",
            name = "Banhos",
            color = Color(0xFFD03A94),
            icon = R.drawable.icone_banho,
            iconVector = R.drawable.icone_banhos_vector,
        )

    val PASSEIO =
        TaskType(
            id = "passeio",
            name = "Passeio",
            color = Color(0xFFB78AF7),
            icon = R.drawable.icone_passeio,
            iconVector = R.drawable.icone_passeios_vector,
        )

    val ALL_TYPES =
        listOf(
            VACINA,
            CONSULTAS,
            RACAO,
            MEDICAMENTOS,
            BANHOS,
            PASSEIO,
        )

    fun getTypeById(id: String): TaskType? {
        return ALL_TYPES.find { it.id == id }
    }

    fun getTypeByName(nome: String): TaskType? {
        return ALL_TYPES.find { it.name.equals(nome, ignoreCase = true) }
    }
}
