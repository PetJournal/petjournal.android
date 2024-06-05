package com.petjournal.database

import com.petjournal.database.converter.Converter.toEntity
import com.petjournal.database.converter.Converter.toModel
import com.petjournal.database.database.dao.GuardianProfileDao
import com.petjournal.database.database.entity.GuardianProfile
import com.petjournal.setup.CAT
import com.petjournal.setup.DOG
import com.petjournal.setup.listPetRaces
import com.petjournal.setup.listPetSizes
import com.petjournal.setup.newPetInformation
import com.petjournal.setup.petInformation
import com.petjournal.setup.profile
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GuardianProfileDaoTest {
    private val guardianProfileDao: GuardianProfileDao = mockk()


    @Before
    fun setUp() {
        coEvery { guardianProfileDao.insertProfile(any()) } returns 1L
        coEvery { guardianProfileDao.getProfile(1) } returns profile
        coEvery { guardianProfileDao.insertPetInformation(any()) } returns 1L
        coEvery { guardianProfileDao.getPetInformation(1) } returns petInformation.toModel()
        coEvery { guardianProfileDao.getPetInformation(2) } returns newPetInformation.toModel()
        coEvery { guardianProfileDao.updatePetInformation(newPetInformation) }
        coEvery { guardianProfileDao.getListPetSizes(CAT) } returns listPetSizes
        coEvery { guardianProfileDao.insertListPetSizes(any()) } returns 1L
        coEvery { guardianProfileDao.getListPetRaces(DOG) } returns listPetRaces
        coEvery { guardianProfileDao.insertListPetRaces(any()) } returns 1L
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
    fun `get list pet sizes must be the same`() = runBlocking {
        val getListPetSizes = guardianProfileDao.getListPetSizes(CAT)
        coEvery { guardianProfileDao.getListPetSizes(CAT) }
        assertEquals(listPetSizes.listPetSizes, getListPetSizes?.listPetSizes)
    }

    @Test
    fun `insert and get list Pet Sizes`() = runBlocking {
        val insertListPetSizes = guardianProfileDao.insertListPetSizes(listPetSizes)
        coEvery { guardianProfileDao.insertListPetSizes(listPetSizes) }
        assertEquals(1L, insertListPetSizes)

        val retrievedListPetSizes = guardianProfileDao.getListPetSizes(CAT)
        coVerify { guardianProfileDao.getListPetSizes(CAT) }
        assertEquals(listPetSizes, retrievedListPetSizes)
    }

    @Test
    fun `get list pet races must be the same`() = runBlocking {
        val getListPetRaces = guardianProfileDao.getListPetRaces(DOG)
        coEvery { guardianProfileDao.getListPetRaces(DOG) }
        assertEquals(listPetRaces.listPetRaces, getListPetRaces?.listPetRaces)
    }

    @Test
    fun `insert and get list Pet Races`() = runBlocking {
        val insertListPetRaces = guardianProfileDao.insertListPetRaces(listPetRaces)
        coEvery { guardianProfileDao.insertListPetRaces(listPetRaces) }
        assertEquals(1L, insertListPetRaces)

        val retrievedListPetRaces = guardianProfileDao.getListPetRaces(DOG)
        coVerify { guardianProfileDao.getListPetRaces(DOG) }
        assertEquals(listPetRaces.listPetRaces, retrievedListPetRaces?.listPetRaces)
    }
}
