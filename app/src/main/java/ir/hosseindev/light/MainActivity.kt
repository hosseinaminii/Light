package ir.hosseindev.light

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ir.hosseindev.light.ui.theme.LightTheme
import ir.hosseindev.light.ui.theme.darkBackgroundColor
import ir.hosseindev.light.ui.theme.lightBackgroundColor
import ir.hosseindev.light.ui.theme.lightColor
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LightTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.premetime)),
        fontWeight = FontWeight.Bold,
        fontSize = TextUnit(90f, TextUnitType.Sp),
        color = Color.Red
    )
    val textSize = remember {
        textMeasurer.measure("LIGHT", style = textStyle).size
    }
    val degree = remember {
        Animatable(0f)
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(color = lightBackgroundColor)
        val lightX = (size.width - textSize.width) / 2 + 76.dp.toPx()
        val lightY = (size.height - textSize.height) / 2 - 55.dp.toPx()
        val path = Path().apply {
            moveTo(x = lightX, y = lightY)
            lineTo(x = lightX - 100.dp.toPx(), lightY + textSize.height + 173.dp.toPx())
            lineTo(x = lightX + 100.dp.toPx(), lightY + textSize.height + 173.dp.toPx())
            close()
        }
        rotate(degrees = degree.value, pivot = Offset(x = lightX, y = lightY + 47.dp.toPx())) {
            drawPath(path, lightColor)
        }

    }

    Canvas(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer {
            compositingStrategy = CompositingStrategy.Offscreen
        }
    ) {
        drawRect(color = darkBackgroundColor)
        drawText(
            textMeasurer, "LIGHT",
            style = textStyle,
            topLeft = Offset(
                x = size.width / 2 - textSize.width / 2,
                y = size.height / 2 - textSize.height / 2
            ),
            blendMode = BlendMode.SrcOut
        )
        drawCircle(
            color = Color.Red,
            radius = 12.dp.toPx(),
            center = Offset(x = size.width / 2 - 80.dp.toPx(), y = size.height / 2 - 60.dp.toPx()),
            blendMode = BlendMode.SrcOut
        )
    }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            degree.animateTo(-70f, tween(durationMillis = 3000))
            delay(200)
            degree.animateTo(42f, tween(durationMillis = 3000))
            delay(200)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LightTheme {
        Content()
    }
}