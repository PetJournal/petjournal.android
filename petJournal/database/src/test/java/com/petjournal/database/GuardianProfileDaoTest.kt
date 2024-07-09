package com.petjournal.database

import com.petjournal.database.converter.Converter.toEntity
import com.petjournal.database.converter.Converter.toModel
import com.petjournal.database.database.dao.GuardianProfileDao
import com.petjournal.database.database.entity.GuardianProfile
import com.petjournal.database.database.entity.PetInformation
import com.petjournal.setup.CAT
import com.petjournal.setup.listPetRace
import com.petjournal.setup.listPetSizes
import com.petjournal.setup.newPetInformation
import com.petjournal.setup.petInformation
import com.petjournal.setup.profile
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
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

    private val newPetInformation2 = PetInformation(
        id = 1,
        species = "Cat",
        name = "Bolinha",
        gender = "F",
        size = "Pequeno",
        petRace = "Akita",
        petAge = "10/10/2010",
        guardianId = 1
    )
    private val petInformationList = listOf(
        newPetInformation2.toModel(),
        newPetInformation.toModel(),
        newPetInformation.toModel()
    )
    @Before
    fun setUp() {
        coEvery { guardianProfileDao.insertProfile(any()) } returns 1L
        coEvery { guardianProfileDao.getProfile(1) } returns profile
        coEvery { guardianProfileDao.insertPetInformation(any()) } returns 1L
        coEvery { guardianProfileDao.getPetInformation(1) } returns petInformation.toModel()
        coEvery { guardianProfileDao.getPetInformation(2) } returns newPetInformation.toModel()
        coEvery { guardianProfileDao.updatePetInformation(newPetInformation) }
        coEvery { guardianProfileDao.getAllPetInformation() } returns petInformationList
        coEvery {
            guardianProfileDao.deletePetInformation(any())
        }returns Unit
        coEvery { guardianProfileDao.getListPetSizes(CAT) } returns listPetSizes
        coEvery { guardianProfileDao.insertListPetSizes(any()) }
        coEvery { guardianProfileDao.getListPetRaces(CAT) } returns listPetRace
        coEvery { guardianProfileDao.insertListPetRaces(any()) }
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

    @Test
    fun `should get all pet information`() = runBlocking {
        val oldPetInformationList = guardianProfileDao.getAllPetInformation()
        coVerify { guardianProfileDao.getAllPetInformation() }
        assertEquals(petInformationList, oldPetInformationList)
    }

    @Test
    fun `should delete pet information`() = runBlocking {
        val deletedItemId : Long = 1
    @Test
    fun `get list pet sizes must be the same`() = runBlocking {
        val getListPetSizes = guardianProfileDao.getListPetSizes(CAT)
        coEvery { guardianProfileDao.getListPetSizes(CAT) }
        assertEquals(listPetSizes, getListPetSizes)
    }

    @Test
    fun `insert and get list Pet Sizes`() = runBlocking {
        coEvery { guardianProfileDao.insertListPetSizes(any()) } just Runs

        guardianProfileDao.insertListPetSizes(listPetSizes)

        coEvery { guardianProfileDao.getListPetSizes(CAT) } returns listPetSizes

        guardianProfileDao.deletePetInformation(deletedItemId)

        coVerify {
            guardianProfileDao.deletePetInformation(deletedItemId)
        }
        val newPetInformationList = petInformationList.toMutableList().also {
            it.removeAll { it.id == deletedItemId }
        }

        assertNotEquals(newPetInformationList, petInformationList)
    }
        val retrievedListPetSizes = guardianProfileDao.getListPetSizes(CAT)

        coVerify { guardianProfileDao.insertListPetSizes(listPetSizes) }
        coVerify { guardianProfileDao.getListPetSizes(CAT) }

        assertEquals(listPetSizes, retrievedListPetSizes)
    }

    @Test
    fun `get list pet races must be the same`() = runBlocking {
        val getListPetRaces = guardianProfileDao.getListPetRaces(CAT)
        coEvery { guardianProfileDao.getListPetRaces(CAT) }
        assertEquals(listPetRace, getListPetRaces)
    }

    @Test
    fun `insert and get list Pet Races`() = runBlocking {
        coEvery { guardianProfileDao.insertListPetRaces(any()) } just Runs

        guardianProfileDao.insertListPetRaces(listPetRace)

        coEvery { guardianProfileDao.getListPetRaces(CAT) } returns listPetRace

        val retrievedListPetRaces = guardianProfileDao.getListPetRaces(CAT)

        coVerify { guardianProfileDao.insertListPetRaces(listPetRace) }
        coVerify { guardianProfileDao.getListPetRaces(CAT) }

        assertEquals(listPetRace, retrievedListPetRaces)

    }
}
