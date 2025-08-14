package com.soujunior.petjournal.ui.components.data

import androidx.compose.ui.graphics.Color

data class TaskType(
    val id: String,
    val nome: String,
    val cor: Color,
    val icone: String? = null // Preparado para uso futuro
)

object TaskTypes {
    val VACINA = TaskType(
        id = "vacina",
        nome = "Vacina",
        cor = Color(0xFFFA680E)
    )

    val CONSULTAS = TaskType(
        id = "consultas",
        nome = "Consultas",
        cor = Color(0xFF20955E)
    )

    val RACAO = TaskType(
        id = "racao",
        nome = "Ração",
        cor = Color(0xFF881803)
    )

    val MEDICAMENTOS = TaskType(
        id = "medicamentos",
        nome = "Medicamentos",
        cor = Color(0xFF2F99E5)
    )

    val BANHOS = TaskType(
        id = "banhos",
        nome = "Banhos",
        cor = Color(0xFFD03A94)
    )

    val PASSEIO = TaskType(
        id = "passeio",
        nome = "Passeio",
        cor = Color(0xFFB78AF7)
    )

    // Lista com todos os tipos para facilitar iteração
    val ALL_TYPES = listOf(
        VACINA,
        CONSULTAS,
        RACAO,
        MEDICAMENTOS,
        BANHOS,
        PASSEIO
    )

    // Função para buscar tipo por ID
    fun getTypeById(id: String): TaskType? {
        return ALL_TYPES.find { it.id == id }
    }

    // Função para buscar tipo por nome
    fun getTypeByName(nome: String): TaskType? {
        return ALL_TYPES.find { it.nome.equals(nome, ignoreCase = true) }
    }
}

data class PetData(
    val id: String,
    val imageRes: String = "" // String vazia por enquanto
)

data class TaskData(
    val id: String,
    val titulo: String,
    val descricaoResumida: String,
    val descricaoCompleta: String,
    val dataHora: String,
    val tipo: TaskType,
    val pets: List<PetData> = emptyList()
)

// Dados dummy para exemplo/preview
object TaskDummyData {
    private val samplePets = listOf(
        PetData(id = "pet_001"),
        PetData(id = "pet_002"),
        PetData(id = "pet_003"),
        PetData(id = "pet_004"),
        PetData(id = "pet_005"),
        PetData(id = "pet_006")
    )

    val sampleTasks = listOf(
        TaskData(
            id = "1",
            titulo = "Carprofeno",
            descricaoResumida = "Anti-inflamatorio não esteroide para alivio da dor e inflamação",
            descricaoCompleta = "Medicamento anti-inflamatório não esteroide para alívio da dor e inflamação. Deve ser administrado com cuidado e seguindo as instruções veterinárias. Dosagem recomendada conforme peso do animal.",
            dataHora = "12/08/2025 - 10:30",
            tipo = TaskTypes.MEDICAMENTOS,
            pets = listOf(samplePets[0], samplePets[1], samplePets[2])
        ),
        TaskData(
            id = "2",
            titulo = "Vacina Antirrábica",
            descricaoResumida = "Vacina obrigatória contra raiva para proteção do pet",
            descricaoCompleta = "Vacina antirrábica anual obrigatória. Essencial para proteção contra a raiva e exigida por lei. Deve ser aplicada por veterinário e gera certificado de vacinação.",
            dataHora = "15/08/2025 - 14:00",
            tipo = TaskTypes.VACINA,
            pets = listOf(samplePets[0])
        ),
        TaskData(
            id = "3",
            titulo = "Consulta de Rotina",
            descricaoResumida = "Check-up geral para avaliação da saúde do pet",
            descricaoCompleta = "Consulta veterinária de rotina para avaliação geral da saúde, verificação de peso, exame físico completo e orientações sobre cuidados preventivos.",
            dataHora = "20/08/2025 - 09:15",
            tipo = TaskTypes.CONSULTAS,
            pets = listOf(samplePets[1], samplePets[3])
        ),
        TaskData(
            id = "4",
            titulo = "Ração Premium",
            descricaoResumida = "Trocar para ração premium conforme orientação veterinária",
            descricaoCompleta = "Mudança gradual para ração premium de alta qualidade. Fazer transição lenta misturando com a ração atual por 7 dias. Quantidade: 200g por dia dividida em 2 refeições.",
            dataHora = "18/08/2025 - 18:00",
            tipo = TaskTypes.RACAO,
            pets = listOf(samplePets[0], samplePets[2], samplePets[4], samplePets[5])
        )
    )
}
