package jp.craftman1take.stationinfoapp.ui.composable.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BoxText(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Unspecified,
    textColor: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
    contentAlignment: Alignment = Alignment.Center,
    text: String = "",
) {
    Box(
        modifier = modifier.background(backgroundColor),
        contentAlignment = contentAlignment,
    ) {
        Text(
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            color = textColor,
            text = text,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BoxTextPreview() {
    BoxText(
        modifier = Modifier.fillMaxWidth().height(128.dp),
        backgroundColor = Color.Blue,
        textColor = Color.White,
        fontSize = 20.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold,
        contentAlignment = Alignment.Center,
        text = "Hello, world.",
    )
}