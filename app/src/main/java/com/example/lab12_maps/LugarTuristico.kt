package com.example.lab12_maps

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

// Modelo de un lugar turístico que puede agruparse con Clustering (Puntos adicionales)
data class LugarTuristico(
    val posicion: LatLng,
    val nombre: String,
    val descripcion: String
) : ClusterItem {
    override fun getPosition(): LatLng = posicion
    override fun getTitle(): String = nombre
    override fun getSnippet(): String = descripcion
    override fun getZIndex(): Float? = null
}

// Lugares turísticos de Arequipa (coordenadas aproximadas)
val lugaresTuristicos = listOf(
    LugarTuristico(LatLng(-16.398500, -71.536800), "Catedral de Arequipa", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.399700, -71.536000), "Iglesia de la Compañía", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.395500, -71.536800), "Monasterio de Santa Catalina", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.400300, -71.537300), "Museo Santuarios Andinos", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.396700, -71.539000), "Casa del Moral", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.397900, -71.535600), "Casa Tristán del Pozo", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.396100, -71.543200), "Monasterio de la Recoleta", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.397500, -71.541300), "Puente Bolognesi", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.402400, -71.532900), "Mercado San Camilo", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.392800, -71.535700), "Mundo Alpaca", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.387800, -71.542000), "Mirador de Yanahuara", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.385500, -71.530000), "Parque Selva Alegre", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.372700, -71.538800), "Mirador de Carmen Alto", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.425300, -71.565000), "Mirador de Sachaca", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.446200, -71.565000), "Mansión del Fundador", "Toca aquí para ver Street View"),
    LugarTuristico(LatLng(-16.453300, -71.491700), "Molino de Sabandía", "Toca aquí para ver Street View")
)
