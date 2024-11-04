package com.mbcoding.wcfinder

import com.google.android.gms.maps.model.LatLng
import com.mbcoding.wcfinder.data.remote.WCApi
import com.mbcoding.wcfinder.data.remote.WCData
import com.mbcoding.wcfinder.data.remote.WCRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class WCRepositoryTest {
    private lateinit var repository: WCRepository
    private val api: WCApi = mock()

    @Before
    fun setup() {
        repository = WCRepository(api)
    }

    @Test
    fun `getWCData should return data`() = runBlocking {
        val location = LatLng(48.876253, 2.301943)
        val distance = 1000
        val start = 0
        val rows = 10
        val mockData = WCData()

        `when`(api.getWCData(location, distance, start, rows)).thenReturn(mockData)

        val result = repository.getWCData(location, distance, start, rows)

        assertEquals(mockData, result)
    }
}