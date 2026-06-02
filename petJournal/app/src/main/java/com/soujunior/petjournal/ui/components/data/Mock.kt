@file:Suppress("ktlint")

package com.soujunior.petjournal.ui.components.data

import com.soujunior.petjournal.ui.model.TaskData

object TaskFakeData {

    val sampleTasks = listOf(
        TaskData(
            id = "1",
            title = "Carprofeno",
            descriptionResumed = "Anti-inflamatorio não esteroide para alivio da dor e inflamação",
            descriptionCompleted = "Medicamento anti-inflamatório não esteroide para alívio da dor e inflamação. Deve ser administrado com cuidado e seguindo as instruções veterinárias. Dosagem recomendada conforme peso do animal.",
            startAt = "12/08/2025 - 10:30",
            type = TaskTypes.MEDICAMENTOS,
            pets = emptyList()
        ),
        TaskData(
            id = "2",
            title = "Vacina Antirrábica",
            descriptionResumed = "Vacina obrigatória contra raiva para proteção do pet",
            descriptionCompleted = "Vacina antirrábica anual obrigatória. Essencial para proteção contra a raiva e exigida por lei. Deve ser aplicada por veterinário e gera certificado de vacinação.",
            startAt = "15/08/2025 - 14:00",
            type = TaskTypes.VACINA,
            pets = emptyList()
        ),
        TaskData(
            id = "3",
            title = "Consulta de Rotina",
            descriptionResumed = "Check-up geral para avaliação da saúde do pet",
            descriptionCompleted = "Consulta veterinária de rotina para avaliação geral da saúde, verificação de peso, exame físico completo e orientações sobre cuidados preventivos.",
            startAt = "20/08/2025 - 09:15",
            type = TaskTypes.CONSULTAS,
            pets = emptyList()
        ),
        TaskData(
            id = "4",
            title = "Ração Premium",
            descriptionResumed = "Trocar para ração premium conforme orientação veterinária",
            descriptionCompleted = "Mudança gradual para ração premium de alta qualidade. Fazer transição lenta misturando com a ração atual por 7 dias. Quantidade: 200g por dia dividida em 2 refeições.",
            startAt = "18/08/2025 - 18:00",
            type = TaskTypes.RACAO,
            pets = emptyList()
        )
    )
}
