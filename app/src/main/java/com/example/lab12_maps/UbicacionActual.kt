package com.example.lab12_maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng

// Permisos necesarios para obtener la ubicación del usuario
val permisosUbicacion = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

// Verifica si el usuario ya concedió al menos uno de los permisos de ubicación
fun tienePermisoUbicacion(context: Context): Boolean {
    return permisosUbicacion.any { permiso ->
        ContextCompat.checkSelfPermission(context, permiso) == PackageManager.PERMISSION_GRANTED
    }
}

// Obtiene la ubicación actual del dispositivo usando FusedLocationProviderClient
@SuppressLint("MissingPermission") // El permiso se verifica antes de llamar a esta función
fun obtenerUbicacionActual(
    context: Context,
    onUbicacion: (LatLng) -> Unit,
    onError: (String) -> Unit
) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
        .addOnSuccessListener { location ->
            if (location != null) {
                onUbicacion(LatLng(location.latitude, location.longitude))
            } else {
                onError("No se pudo obtener la ubicación. Verifica que el GPS esté activado.")
            }
        }
        .addOnFailureListener { e ->
            onError("Error al obtener la ubicación: ${e.message}")
        }
}
