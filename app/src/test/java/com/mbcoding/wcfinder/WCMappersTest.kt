package com.mbcoding.wcfinder

import com.mbcoding.wcfinder.data.mapper.toWC
import com.mbcoding.wcfinder.data.remote.Fields
import com.mbcoding.wcfinder.data.remote.Geometry
import com.mbcoding.wcfinder.data.remote.Record
import junit.framework.TestCase.assertEquals
import org.junit.Test

class WCMappersTest {

    @Test
    fun `toWC mapping fields`() {
        val record = Record(
            fields = Fields(
                type = "Sanisette",
                adresse = "12 rue de Paris",
                dist = "234.567",
                horaire = "24 h / 24"
            ),
            geometry = Geometry(coordinates = listOf(2.3522, 48.8566))
        )

        val wc = record.toWC()

        assertEquals("Sanisette", wc.name)
        assertEquals("12 RUE DE PARIS", wc.address)
        assertEquals(234, wc.distance)
        assertEquals("24 h / 24", wc.hours)
        assertEquals(48.8566, wc.latitude!!, 0.001)
        assertEquals(2.3522, wc.longitude!!, 0.001)
    }
}