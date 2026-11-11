package com.example.lab12_maps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch

@Composable
fun MapScreen() {
    val context = LocalContext.current
    val ArequipaLocation = LatLng(-16.4040102, -71.559611) // Arequipa, Perú

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(ArequipaLocation, 12f)
    }

    // Tipo de mapa seleccionado (Ejercicio 1)
    var tipoDeMapa by remember { mutableStateOf(MapType.NORMAL) }

    // ----- Ubicación actual del usuario (Ejercicio 2) -----
    val scope = rememberCoroutineScope()
    var permisoUbicacion by remember { mutableStateOf(tienePermisoUbicacion(context)) }
    var miUbicacion by remember { mutableStateOf<LatLng?>(null) }

    // Obtiene la ubicación y mueve la cámara hacia ella
    fun centrarEnMiUbicacion() {
        obtenerUbicacionActual(
            context = context,
            onUbicacion = { ubicacion ->
                miUbicacion = ubicacion
                scope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLngZoom(ubicacion, 16f),
                        durationMs = 1500
                    )
                }
            },
            onError = { mensaje ->
                Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
            }
        )
    }

    // Solicita los permisos de ubicación en tiempo de ejecución
    val launcherPermisos = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultados ->
        permisoUbicacion = resultados.values.any { it }
        if (permisoUbicacion) {
            centrarEnMiUbicacion()
        } else {
            Toast.makeText(context, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
    }

    // Mover la cámara a Yura con animación al iniciar la pantalla
    LaunchedEffect(Unit) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(LatLng(-16.2520984, -71.6836503), 12f), // Mover a Yura
            durationMs = 3000
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Añadir GoogleMap al layout
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                mapType = tipoDeMapa,
                isMyLocationEnabled = permisoUbicacion // Punto azul de "mi ubicación"
            ),
            uiSettings = MapUiSettings(myLocationButtonEnabled = false) // Usamos nuestro propio botón
        ) {
            // Marcador en la ubicación actual del usuario (Ejercicio 2)
            miUbicacion?.let { ubicacion ->
                key(ubicacion) {
                    Marker(
                        state = rememberMarkerState(position = ubicacion),
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN),
                        title = "Mi ubicación",
                        snippet = "Lat: %.5f, Lng: %.5f".format(ubicacion.latitude, ubicacion.longitude)
                    )
                }
            }

            // Icono personalizado de montaña (res/drawable/ic_montana.xml)
            val iconoMontana = remember {
                bitmapDescriptorFromVector(context, R.drawable.ic_montana)
            }

            // Añadir marcador en Arequipa, Perú
            Marker(
                state = rememberMarkerState(position = ArequipaLocation),
                icon = iconoMontana, // Icono personalizado
                title = "Arequipa, Perú"
            )

            // Añadir varios marcadores (puntos de interés)
            val locations = listOf(
                LatLng(-16.433415, -71.5442652), // JLByR
                LatLng(-16.4205151, -71.4945209), // Paucarpata
                LatLng(-16.3524187, -71.5675994) // Zamacola
            )

            locations.forEach { location ->
                Marker(
                    state = rememberMarkerState(position = location),
                    title = "Ubicación",
                    snippet = "Punto de interés"
                )
            }

            // Dibujar polígonos en el mapa
            val mallAventuraPolygon = listOf(
                LatLng(-16.432292, -71.509145),
                LatLng(-16.432757, -71.509626),
                LatLng(-16.433013, -71.509310),
                LatLng(-16.432566, -71.508853)
            )

            val parqueLambramaniPolygon = listOf(
                LatLng(-16.422704, -71.530830),
                LatLng(-16.422920, -71.531340),
                LatLng(-16.423264, -71.531110),
                LatLng(-16.423050, -71.530600)
            )

            val plazaDeArmasPolygon = listOf(
                LatLng(-16.398866, -71.536961),
                LatLng(-16.398744, -71.536529),
                LatLng(-16.399178, -71.536289),
                LatLng(-16.399299, -71.536721)
            )

            Polygon(
                points = plazaDeArmasPolygon,
                strokeColor = Color.Red,
                fillColor = Color.Blue,
                strokeWidth = 5f
            )

            Polygon(
                points = parqueLambramaniPolygon,
                strokeColor = Color.Red,
                fillColor = Color.Blue,
                strokeWidth = 5f
            )

            Polygon(
                points = mallAventuraPolygon,
                strokeColor = Color.Red,
                fillColor = Color.Blue,
                strokeWidth = 5f
            )

            // ----- Polilíneas -----

            // Ejemplo 1: Ruta turística Plaza de Armas -> Parque Lambramani -> Mall Aventura
            // Línea continua naranja con esquinas y extremos redondeados
            val rutaTuristica = listOf(
                LatLng(-16.399022, -71.536625), // Plaza de Armas
                LatLng(-16.422985, -71.530970), // Parque Lambramani
                LatLng(-16.432657, -71.509234)  // Mall Aventura
            )
            Polyline(
                points = rutaTuristica,
                color = Color(0xFFE65100),
                width = 12f,
                jointType = JointType.ROUND,
                startCap = RoundCap(),
                endCap = RoundCap()
            )

            // Ejemplo 2: Arequipa -> Yura
            // Línea discontinua (guiones) azul y geodésica
            val rutaYura = listOf(
                ArequipaLocation,
                LatLng(-16.2520984, -71.6836503) // Yura
            )
            Polyline(
                points = rutaYura,
                color = Color(0xFF1565C0),
                width = 8f,
                geodesic = true,
                pattern = listOf(Dash(30f), Gap(20f))
            )

            // Ejemplo 3: Recorrido por los puntos de interés JLByR -> Paucarpata -> Zamacola
            // Línea punteada morada
            val recorridoPuntos = listOf(
                LatLng(-16.433415, -71.5442652), // JLByR
                LatLng(-16.4205151, -71.4945209), // Paucarpata
                LatLng(-16.3524187, -71.5675994) // Zamacola
            )
            Polyline(
                points = recorridoPuntos,
                color = Color(0xFF8E24AA),
                width = 10f,
                pattern = listOf(Dot(), Gap(15f))
            )
        }

        // Selector de tipo de mapa sobre el mapa (Ejercicio 1)
        MapTypeSelector(
            tipoSeleccionado = tipoDeMapa,
            onTipoSeleccionado = { tipoDeMapa = it },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 8.dp)
        )

        // Botón para ir a la ubicación actual (Ejercicio 2)
        ExtendedFloatingActionButton(
            onClick = {
                if (permisoUbicacion) {
                    centrarEnMiUbicacion()
                } else {
                    launcherPermisos.launch(permisosUbicacion)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .navigationBarsPadding()
                .padding(16.dp)
        ) {
            Text("📍 Mi ubicación")
        }
    }
}

// Convierte un drawable vectorial en un BitmapDescriptor para usarlo como icono del marcador
fun bitmapDescriptorFromVector(context: Context, @DrawableRes vectorResId: Int): BitmapDescriptor {
    val drawable = ContextCompat.getDrawable(context, vectorResId)!!
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    drawable.draw(Canvas(bitmap))
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
