package com.example.lab12_maps

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.StreetViewPanoramaOptions
import com.google.android.gms.maps.model.StreetViewSource
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.streetview.StreetView
import com.google.maps.android.ktx.MapsExperimentalFeature

// Pantalla de Street View de un lugar turístico (Puntos adicionales)
@OptIn(MapsComposeExperimentalApi::class, MapsExperimentalFeature::class)
@Composable
fun StreetViewScreen(
    lugar: LugarTuristico,
    onCerrar: () -> Unit
) {
    // El botón "Atrás" del celular regresa al mapa
    BackHandler { onCerrar() }

    Box(modifier = Modifier.fillMaxSize()) {
        StreetView(
            modifier = Modifier.fillMaxSize(),
            streetViewPanoramaOptionsFactory = {
                StreetViewPanoramaOptions()
                    // Busca la panorámica exterior más cercana en un radio de 100 metros
                    .position(lugar.posicion, 100, StreetViewSource.OUTDOOR)
            }
        )

        // Barra superior con el nombre del lugar y el botón para volver
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.6f))
                .statusBarsPadding()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = lugar.nombre,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Street View · arrastra para mirar alrededor",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 13.sp
            )
            Button(
                onClick = onCerrar,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("← Volver al mapa")
            }
        }
    }
}
