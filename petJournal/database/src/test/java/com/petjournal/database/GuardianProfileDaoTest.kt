package com.petjournal.database

import com.petjournal.database.converter.Converter.toEntity
import com.petjournal.database.converter.Converter.toModel
import com.petjournal.database.database.dao.GuardianProfileDao
import com.petjournal.database.database.entity.GuardianProfile
import com.petjournal.database.database.entity.PetInformation
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GuardianProfileDaoTest {
    private val guardianProfileDao: GuardianProfileDao = mockk()
    private val profile = GuardianProfile(
        firstName = null
    )
    private val petInformation = PetInformation(
        species = "Dog",
        guardianId = 1
    )
    private val newPetInformation = PetInformation(
        id = 2,
        species = "Cat",
        name = "Bolinha",
        gender = "F",
        size = "Pequeno",
        petRace = "Akita",
        petAge = "10/10/2010",
        guardianId = 1
    )

    @Before
    fun setUp() {
        coEvery { guardianProfileDao.insertProfile(any()) } returns 1L
        coEvery { guardianProfileDao.getProfile(1) } returns profile
        coEvery { guardianProfileDao.insertPetInformation(any()) } returns 1L
        coEvery { guardianProfileDao.getPetInformation(1) } returns petInformation.toModel()
        coEvery { guardianProfileDao.getPetInformation(2) } returns newPetInformation.toModel()
        coEvery { guardianProfileDao.updatePetInformation(newPetInformation) }
    }

    @Test
    fun insertAndGetProfile() = runBlocking {
        val insertId = guardianProfileDao.insertProfile(profile)
        coVerify { guardianProfileDao.insertProfile(profile) }
        assertEquals(1L, insertId)
        val retrievedProfile = guardianProfileDao.getProfile(insertId.toInt())
        coVerify { guardianProfileDao.getProfile(1) }
        assertEquals(profile, retrievedProfile)
    }

    @Test
    fun insertAndGetProfileWithNullName() = runBlocking {
        val profileWithNullName = GuardianProfile(firstName = null)
        coEvery { guardianProfileDao.insertProfile(profileWithNullName) } returns 1L
        coEvery { guardianProfileDao.getProfile(1) } returns profileWithNullName
        val insertId = guardianProfileDao.insertProfile(profileWithNullName)
        val retrievedProfile = guardianProfileDao.getProfile(insertId.toInt())
        coVerify {
            guardianProfileDao.insertProfile(profileWithNullName)
            guardianProfileDao.getProfile(1)
        }
        assertEquals(profileWithNullName, retrievedProfile)
    }

    @Test
    fun `insert and get pet information`() = runBlocking {
        val insertPetInformation = guardianProfileDao.insertPetInformation(petInformation)
        coEvery { guardianProfileDao.insertPetInformation(petInformation) }
        assertEquals(1L, insertPetInformation)
        val retrievedPetInformation = guardianProfileDao.getPetInformation(insertPetInformation)
        coVerify { guardianProfileDao.getPetInformation(1) }
        assertEquals(petInformation, retrievedPetInformation.toEntity())
    }

    @Test
    fun `update and get pet information must be the same`() = runBlocking {

        coEvery { guardianProfileDao.updatePetInformation(newPetInformation) }
        val getPetInformation = guardianProfileDao.getPetInformation(2)
        coVerify { guardianProfileDao.getPetInformation(2) }
        assertEquals(newPetInformation, getPetInformation.toEntity())

    }


}
