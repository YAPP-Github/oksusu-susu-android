package com.susu.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.susu.domain.repository.TokenRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@OptIn(ExperimentalCoroutinesApi::class)
class TokenRepositoryImplTest {

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()
    private val testScope = TestScope(UnconfinedTestDispatcher() + Job())
    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = testScope,
        produceFile = {
            tmpFolder.newFile("test.preferences_pb")
        }
    )
    private val subject: TokenRepository = TokenRepositoryImpl(testDataStore)

    @Test
    fun whenGetAccessTokenFirstTime_thenReturnNull() = testScope.runTest {
        assertEquals(subject.getAccessToken().first(), null)
    }

    @Test
    fun whenSaveAccessToken_thenReturnUpdatedToken() = testScope.runTest {
        val accessToken = "token"
        subject.saveAccessToken(accessToken)
        assertEquals(subject.getAccessToken().first(), accessToken)
    }

    @Test
    fun whenDeleteAccessToken_thenReturnNull() = testScope.runTest {
        subject.deleteAccessToken()
        assertEquals(subject.getAccessToken().first(), null)
    }
}
