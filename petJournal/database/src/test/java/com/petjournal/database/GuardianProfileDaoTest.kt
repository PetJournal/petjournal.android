package com.petjournal.database

import com.petjournal.database.dao.GuardianProfileDao
import com.petjournal.database.entity.GuardianProfile
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

    @Before
    fun setUp() {
        coEvery { guardianProfileDao.insertProfile(any()) } returns 1L
        coEvery { guardianProfileDao.getProfile(1) } returns profile
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

}
