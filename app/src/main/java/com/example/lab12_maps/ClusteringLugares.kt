package com.example.lab12_maps

import androidx.compose.runtime.Composable
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering

// Agrupa los lugares turísticos cercanos en clusters (Maps Utils)
@OptIn(MapsComposeExperimentalApi::class)
@Composable
@GoogleMapComposable
fun ClusteringLugares(
    lugares: List<LugarTuristico>,
    onClusterClick: (Cluster<LugarTuristico>) -> Unit,
    onVerStreetView: (LugarTuristico) -> Unit
) {
    Clustering(
        items = lugares,
        // Al tocar un grupo, se hace zoom para separar los lugares
        onClusterClick = { cluster ->
            onClusterClick(cluster)
            true
        },
        // Al tocar un lugar se muestra su ventana de información (comportamiento por defecto)
        onClusterItemClick = { false },
        // Al tocar la ventana de información se abre Street View de ese lugar
        onClusterItemInfoWindowClick = { lugar ->
            onVerStreetView(lugar)
        }
    )
}
