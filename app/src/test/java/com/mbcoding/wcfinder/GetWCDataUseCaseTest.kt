package com.mbcoding.wcfinder

import com.google.android.gms.maps.model.LatLng
import com.mbcoding.wcfinder.data.remote.Fields
import com.mbcoding.wcfinder.data.remote.Geometry
import com.mbcoding.wcfinder.data.remote.Record
import com.mbcoding.wcfinder.data.remote.WCData
import com.mbcoding.wcfinder.data.remote.WCRepository
import com.mbcoding.wcfinder.domain.model.WC
import com.mbcoding.wcfinder.domain.usecase.GEO_DISTANCE
import com.mbcoding.wcfinder.domain.usecase.GetWCDataUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock


class GetWCDataUseCaseTest {
    private lateinit var getWCDataUseCase: GetWCDataUseCase
    private val repository: WCRepository = mock()

    @Before
    fun setup() {
        getWCDataUseCase = GetWCDataUseCase(repository)
    }

    @Test
    fun `execute should return data`() = runTest {
        val location = LatLng(48.56, 2.345)
        val start = 0
        val rows = 10
        val mockData = WCData(
            records = listOf(
                Record(
                    fields = Fields(type = "A", adresse = "C", horaire = "1", dist = "1000"),
                    geometry = Geometry(listOf(0.0, 0.0))
                ),
                Record(
                    fields = Fields(type = "B", adresse = "D", horaire = "2", dist = "2000"),
                    geometry = Geometry(coordinates = listOf(0.0, 0.0))
                )
            )
        )

        `when`(repository.getWCData(location, GEO_DISTANCE, start, rows)).thenReturn(mockData)

        val result = getWCDataUseCase.execute(location, start, rows)

        val expected = listOf(
            WC("A", "C", 1000, "1", 0.0, 0.0),
            WC("B", "D", 2000, "2", 0.0, 0.0)
        )

        assertEquals(expected, result)
    }
}