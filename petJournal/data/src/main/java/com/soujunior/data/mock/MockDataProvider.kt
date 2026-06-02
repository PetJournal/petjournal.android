package com.soujunior.data.mock

import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.AccessTokenResponse
import com.soujunior.domain.model.response.UserInfoResponse
import com.soujunior.domain.model.response.MessageResponse
import com.soujunior.domain.model.response.GuardianNameResponse

object MockDataProvider {
    
    const val MOCK_EMAIL = "petjournal@hostname.com"
    const val MOCK_PASSWORD = "aaAA@@12"
    const val MOCK_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJwZXRqb3VybmFsQGhvc3RuYW1lLmNvbSIsImV4cCI6MTY5OTk5OTk5OX0.fake_signature_for_testing"
    
    fun getMockAccessTokenResponse(): AccessTokenResponse {
        return AccessTokenResponse(accessToken = MOCK_TOKEN)
    }
    
    fun getMockUserInfoResponse(): UserInfoResponse {
        return UserInfoResponse(
            id = "mock-user-id-123",
            firstName = "Pet",
            lastName = "Journal",
            email = MOCK_EMAIL,
            phone = "+5511999999999"
        )
    }
    
    fun getMockMessageResponse(message: String = "Operação realizada com sucesso"): MessageResponse {
        return MessageResponse(
            message = message
        )
    }
    
    fun getMockGuardianNameResponse(): GuardianNameResponse {
        return GuardianNameResponse(
            firstName = "Pet",
            lastName = "Journal"
        )
    }
    
    fun getMockPetSizes(specieId: String): List<PetSizeItemModel> {
        return when (specieId.lowercase()) {
            "dog", "cachorro" -> listOf(
                PetSizeItemModel(id = "1", name = "Pequeno", specieId = specieId),
                PetSizeItemModel(id = "2", name = "Médio", specieId = specieId),
                PetSizeItemModel(id = "3", name = "Grande", specieId = specieId),
                PetSizeItemModel(id = "4", name = "Gigante", specieId = specieId)
            )
            "cat", "gato" -> listOf(
                PetSizeItemModel(id = "1", name = "Pequeno", specieId = specieId),
                PetSizeItemModel(id = "2", name = "Médio", specieId = specieId),
                PetSizeItemModel(id = "3", name = "Grande", specieId = specieId)
            )
            else -> listOf(
                PetSizeItemModel(id = "1", name = "Pequeno", specieId = specieId),
                PetSizeItemModel(id = "2", name = "Médio", specieId = specieId)
            )
        }
    }
    
    fun getMockPetRaces(specieId: String): List<PetRaceItemModel> {
        return when (specieId.lowercase()) {
            "dog", "cachorro" -> listOf(
                PetRaceItemModel(id = "1", name = "Labrador", specieId = specieId),
                PetRaceItemModel(id = "2", name = "Golden Retriever", specieId = specieId),
                PetRaceItemModel(id = "3", name = "Bulldog", specieId = specieId),
                PetRaceItemModel(id = "4", name = "Poodle", specieId = specieId),
                PetRaceItemModel(id = "5", name = "Rottweiler", specieId = specieId),
                PetRaceItemModel(id = "6", name = "Pastor Alemão", specieId = specieId),
                PetRaceItemModel(id = "7", name = "SRD", specieId = specieId)
            )
            "cat", "gato" -> listOf(
                PetRaceItemModel(id = "1", name = "Persa", specieId = specieId),
                PetRaceItemModel(id = "2", name = "Siamês", specieId = specieId),
                PetRaceItemModel(id = "3", name = "Maine Coon", specieId = specieId),
                PetRaceItemModel(id = "4", name = "Bengal", specieId = specieId),
                PetRaceItemModel(id = "5", name = "Ragdoll", specieId = specieId),
                PetRaceItemModel(id = "6", name = "SRD", specieId = specieId)
            )
            else -> listOf(
                PetRaceItemModel(id = "1", name = "Raça 1", specieId = specieId),
                PetRaceItemModel(id = "2", name = "SRD", specieId = specieId)
            )
        }
    }
}
