package com.example.lab12_maps

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.MapType

// Tipos de mapa disponibles con su nombre en español
private val tiposDeMapa = listOf(
    MapType.NORMAL to "Normal",
    MapType.SATELLITE to "Satélite",
    MapType.HYBRID to "Híbrido",
    MapType.TERRAIN to "Terreno"
)

// Barra de botones para elegir el tipo de mapa
@Composable
fun MapTypeSelector(
    tipoSeleccionado: MapType,
    onTipoSeleccionado: (MapType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(24.dp))
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tiposDeMapa.forEach { (tipo, nombre) ->
            FilterChip(
                selected = tipo == tipoSeleccionado,
                onClick = { onTipoSeleccionado(tipo) },
                label = { Text(nombre) }
            )
        }
    }
}
