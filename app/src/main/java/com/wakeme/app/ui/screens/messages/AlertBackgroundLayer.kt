package com.wakeme.app.ui.screens.messages

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.dp
import com.wakeme.app.domain.model.AlertBackgroundSettings
import com.wakeme.app.domain.model.AlertBackgroundType
import com.wakeme.app.domain.model.AlertGraphicPattern
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

@Composable
fun AlertBackgroundLayer(
    settings: AlertBackgroundSettings,
    modifier: Modifier = Modifier
) {
    val baseColor = Color(settings.color.storageValue.toInt())
    Box(modifier = modifier.clipToBounds().background(baseColor)) {
        when (settings.type) {
            AlertBackgroundType.SOLID -> Unit
            AlertBackgroundType.EMOJI_PATTERN -> EmojiPatternLayer(
                emoji = settings.emojiPattern.emoji,
                sizeDp = settings.emojiPattern.sizeDp,
                spacingDp = settings.emojiPattern.spacingDp,
                modifier = Modifier.fillMaxSize()
            )
            AlertBackgroundType.GRAPHIC_PATTERN -> GraphicPatternLayer(
                pattern = settings.graphicPattern,
                modifier = Modifier.fillMaxSize()
            )
            AlertBackgroundType.DEVICE_IMAGE -> SelectedPhotoLayer(
                uriString = settings.imageUri,
                imageFit = settings.imageFit,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun EmojiPatternLayer(
    emoji: String,
    sizeDp: Float,
    spacingDp: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val textSizePx = sizeDp.dp.toPx()
        val stepX = (sizeDp + spacingDp).dp.toPx().coerceAtLeast(textSizePx)
        val stepY = (sizeDp + spacingDp).dp.toPx().coerceAtLeast(textSizePx)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = textSizePx
            textAlign = Paint.Align.CENTER
            typeface = Typeface.DEFAULT
            alpha = 150
        }
        drawIntoCanvas { composeCanvas ->
            val canvas = composeCanvas.nativeCanvas
            var row = 0
            var y = textSizePx
            while (y < size.height + textSizePx) {
                val rowOffset = if (row % 2 == 0) 0f else stepX / 2f
                var x = rowOffset
                while (x < size.width + stepX) {
                    canvas.drawText(emoji, x, y, paint)
                    x += stepX
                }
                y += stepY
                row += 1
            }
        }
    }
}

@Composable
private fun GraphicPatternLayer(
    pattern: AlertGraphicPattern,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val color = Color.White.copy(alpha = 0.16f)
        val cell = 72.dp.toPx()
        val stroke = 2.dp.toPx()

        when (pattern) {
            AlertGraphicPattern.GEOMETRIC -> {
                var y = -cell
                while (y < size.height + cell) {
                    var x = -cell
                    while (x < size.width + cell) {
                        drawCircle(color, radius = cell * 0.22f, center = Offset(x + cell / 2f, y + cell / 2f), style = Stroke(stroke))
                        drawLine(color, Offset(x, y + cell), Offset(x + cell, y), strokeWidth = stroke)
                        x += cell
                    }
                    y += cell
                }
            }
            AlertGraphicPattern.ALERT_SYMBOLS -> {
                var y = -cell
                while (y < size.height + cell) {
                    var x = -cell
                    while (x < size.width + cell) {
                        val triangle = Path().apply {
                            moveTo(x + cell / 2f, y + cell * 0.15f)
                            lineTo(x + cell * 0.9f, y + cell * 0.85f)
                            lineTo(x + cell * 0.1f, y + cell * 0.85f)
                            close()
                        }
                        drawPath(triangle, color, style = Stroke(stroke))
                        drawLine(color, Offset(x + cell / 2f, y + cell * 0.38f), Offset(x + cell / 2f, y + cell * 0.60f), strokeWidth = stroke * 1.5f)
                        drawCircle(color, radius = stroke, center = Offset(x + cell / 2f, y + cell * 0.7f))
                        x += cell
                    }
                    y += cell
                }
            }
            AlertGraphicPattern.ABSTRACT -> {
                var y = -cell
                while (y < size.height + cell) {
                    var x = -cell
                    while (x < size.width + cell) {
                        drawOval(color, topLeft = Offset(x + cell * 0.12f, y + cell * 0.25f), size = Size(cell * 0.75f, cell * 0.35f))
                        drawCircle(color.copy(alpha = 0.28f), radius = cell * 0.18f, center = Offset(x + cell * 0.78f, y + cell * 0.76f))
                        x += cell
                    }
                    y += cell
                }
            }
            AlertGraphicPattern.PLAYFUL -> {
                var y = -cell
                while (y < size.height + cell) {
                    var x = -cell
                    while (x < size.width + cell) {
                        val center = Offset(x + cell / 2f, y + cell / 2f)
                        drawCircle(color, radius = cell * 0.12f, center = center)
                        drawLine(color, Offset(center.x - cell * 0.36f, center.y), Offset(center.x + cell * 0.36f, center.y), strokeWidth = stroke)
                        drawLine(color, Offset(center.x, center.y - cell * 0.36f), Offset(center.x, center.y + cell * 0.36f), strokeWidth = stroke)
                        x += cell
                    }
                    y += cell
                }
            }
        }
    }
}

@Composable
private fun SelectedPhotoLayer(
    uriString: String?,
    imageFit: com.wakeme.app.domain.model.AlertImageFit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current.applicationContext
    val image = produceState<Bitmap?>(initialValue = null, uriString) {
        value = uriString?.let { uri ->
            withContext(Dispatchers.IO) { decodeSelectedImage(context, Uri.parse(uri)) }
        }
    }.value

    if (image != null) {
        Image(
            bitmap = image.asImageBitmap(),
            contentDescription = null,
            modifier = modifier,
            contentScale = when (imageFit) {
                com.wakeme.app.domain.model.AlertImageFit.FILL -> ContentScale.Crop
                com.wakeme.app.domain.model.AlertImageFit.FIT -> ContentScale.Fit
            }
        )
    }
}

private fun decodeSelectedImage(context: Context, uri: Uri): Bitmap? {
    return try {
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        context.contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, bounds) }
        if (bounds.outWidth <= 0 || bounds.outHeight <= 0) return null

        val sampleSize = calculateSampleSize(bounds.outWidth, bounds.outHeight)
        val options = BitmapFactory.Options().apply { inSampleSize = sampleSize }
        context.contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, options) }
    } catch (_: IOException) {
        null
    } catch (_: SecurityException) {
        null
    }
}

private fun calculateSampleSize(width: Int, height: Int): Int {
    var sampleSize = 1
    while (width / sampleSize > 1200 || height / sampleSize > 1200) {
        sampleSize *= 2
    }
    return sampleSize
}
