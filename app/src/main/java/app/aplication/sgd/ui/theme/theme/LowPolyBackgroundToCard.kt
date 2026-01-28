package app.aplication.sgd.ui.theme.theme
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import app.aplication.sgd.ui.theme.background.adjustBrightness
import app.aplication.sgd.ui.theme.background.interpolateColor
import kotlin.random.Random


@Composable
fun LowPolyBackgroundToCard(
    modifier: Modifier = Modifier
) {
    // 🔒 Generar polígonos UNA SOLA VEZ por tamaño
    val polygons = remember {
        mutableStateOf<List<Polygon>>(emptyList())
    }

    Canvas(modifier = modifier.fillMaxSize()) {

        // Inicializar solo una vez cuando size esté disponible
        if (polygons.value.isEmpty()) {
            polygons.value = generateLowPolyTriangles1(size.width, size.height)
        }

        // Fondo degradado
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFB6B5B5),
                    Color(0xFFA9A8A8),
                    Color(0xFFA8A7A7),
                    Color(0xFF7E7D7D),
                    Color(0xFF484747),

                )
            )
        )

        polygons.value.forEach {
            drawPolygon1(it)
        }
    }
}



data class Polygon(
    val color: Color,
    val points: List<Offset>
)

fun generateLowPolyTriangles1(width: Float, height: Float): List<Polygon> {
    val polygons = mutableListOf<Polygon>()
    val gridRows = 3  // Reducido de 12 a 7
    val gridCols = 2  // Reducido de 8 a 5
    val cellHeight = height / gridRows
    val cellWidth = width / gridCols

    // Generar puntos base en una cuadrícula
    val points = mutableListOf<Offset>()
    for (row in 0..gridRows) {
        for (col in 0..gridCols) {
            val x = col * cellWidth + (Random.nextFloat() - 0.5f) * cellWidth * 0.5f
            val y = row * cellHeight + (Random.nextFloat() - 0.5f) * cellHeight * 0.5f
            points.add(Offset(x.coerceIn(0f, width), y.coerceIn(0f, height)))
        }
    }

    // Crear triángulos a partir de los puntos
    for (row in 0 until gridRows) {
        for (col in 0 until gridCols) {
            val index = row * (gridCols + 1) + col

            // Obtener el color basado en la posición vertical
            val verticalProgress = row.toFloat() / gridRows
            val baseColor = interpolateColor(verticalProgress)
            val colorVariation = (Random.nextFloat() - 0.5f) * 0.15f
            val finalColor = adjustBrightness(baseColor, colorVariation)

            // Triángulo superior
            if (index + gridCols + 2 < points.size) {
                polygons.add(
                    Polygon(
                        color = finalColor.copy(alpha = 0.85f),
                        points = listOf(
                            points[index],
                            points[index + 1],
                            points[index + gridCols + 1]
                        )
                    )
                )

                // Triángulo inferior
                polygons.add(
                    Polygon(
                        color = adjustBrightness(finalColor, -0.05f).copy(alpha = 0.85f),
                        points = listOf(
                            points[index + 1],
                            points[index + gridCols + 2],
                            points[index + gridCols + 1]
                        )
                    )
                )
            }
        }
    }

    return polygons
}

fun interpolateColor1(progress: Float): Color {
    val colors = listOf(
        Color(0xFFAFADAD), // Blanco
        Color(0xFFA1A0A0), // Gris muy claro
        Color(0xFF9D9B9B), // Gris claro
        Color(0xFF626161), // Gris medio
        Color(0xFF4F4F4F), // Gris

    )

    val index = (progress * (colors.size - 1)).toInt().coerceIn(0, colors.size - 2)
    val localProgress = (progress * (colors.size - 1)) - index

    val startColor = colors[index]
    val endColor = colors[index + 1]

    return Color(
        red = startColor.red + (endColor.red - startColor.red) * localProgress,
        green = startColor.green + (endColor.green - startColor.green) * localProgress,
        blue = startColor.blue + (endColor.blue - startColor.blue) * localProgress
    )
}

fun adjustBrightness1(color: Color, adjustment: Float): Color {
    return Color(
        red = (color.red + adjustment).coerceIn(0f, 1f),
        green = (color.green + adjustment).coerceIn(0f, 1f),
        blue = (color.blue + adjustment).coerceIn(0f, 1f)
    )
}

fun DrawScope.drawPolygon1(polygon: Polygon) {
    val path = Path().apply {
        moveTo(polygon.points.first().x, polygon.points.first().y)
        polygon.points.drop(1).forEach {
            lineTo(it.x, it.y)
        }
        close()
    }
    drawPath(path = path, color = polygon.color)
}