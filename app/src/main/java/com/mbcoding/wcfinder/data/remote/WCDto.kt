package com.mbcoding.wcfinder.data.remote

import com.google.gson.annotations.SerializedName

data class WCData(
    @SerializedName("nhits")
    val nhits: Int? = null,
    @SerializedName("parameters")
    val parameters: Parameters? = null,
    @SerializedName("records")
    val records: List<Record?>? = null
)

data class Parameters(
    @SerializedName("dataset")
    val dataset: String? = null,
    @SerializedName("format")
    val format: String? = null,
    @SerializedName("geofilter.distance")
    val geofilterDistance: List<String?>? = null,
    @SerializedName("rows")
    val rows: Int? = null,
    @SerializedName("start")
    val start: Int? = null,
    @SerializedName("timezone")
    val timezone: String? = null
)

data class Record(
    @SerializedName("datasetid")
    val datasetid: String? = null,
    @SerializedName("fields")
    val fields: Fields? = null,
    @SerializedName("geometry")
    val geometry: Geometry? = null,
    @SerializedName("record_timestamp")
    val recordTimestamp: String? = null,
    @SerializedName("recordid")
    val recordid: String? = null
)

data class Fields(
    @SerializedName("acces_pmr")
    val accesPmr: String? = null,
    @SerializedName("adresse")
    val adresse: String? = null,
    @SerializedName("arrondissement")
    val arrondissement: Int? = null,
    @SerializedName("complement_adresse")
    val complementAdresse: String? = null,
    @SerializedName("dist")
    val dist: String? = null,
    @SerializedName("geo_point_2d")
    val geoPoint2d: List<Double?>? = null,
    @SerializedName("geo_shape")
    val geoShape: GeoShape? = null,
    @SerializedName("gestionnaire")
    val gestionnaire: String? = null,
    @SerializedName("horaire")
    val horaire: String? = null,
    @SerializedName("relais_bebe")
    val relaisBebe: String? = null,
    @SerializedName("source")
    val source: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("url_fiche_equipement")
    val urlFicheEquipement: String? = null
)

data class Geometry(
    @SerializedName("coordinates")
    val coordinates: List<Double?>? = null,
    @SerializedName("type")
    val type: String? = null
)

data class GeoShape(
    @SerializedName("coordinates")
    val coordinates: List<List<Double?>?>? = null,
    @SerializedName("type")
    val type: String? = null
)