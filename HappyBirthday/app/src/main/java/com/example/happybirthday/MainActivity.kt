package com.example.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happybirthday.ui.theme.HappyBirthdayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyBirthdayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF0D1117)
                ) {
                    BirthdayCard(
                        message = "Happy Birthday Arthur!",
                        from = "— Micah"
                    )
                }
            }
        }
    }
}

@Composable
fun BirthdayCard(message: String, from: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D1117), Color(0xFF161B22)) // Warna gelap dengan sedikit gradasi
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
                .shadow(15.dp, RoundedCornerShape(25.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF1E3A8A), Color(0xFF0F172A)) // Gradient biru gelap agar kontras
                    ),
                    shape = RoundedCornerShape(25.dp)
                )
                .padding(32.dp)
        ) {
            Text(
                text = message,
                fontSize = 48.sp,
                lineHeight = 58.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFDBEAFE),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Divider(color = Color(0xFF60A5FA), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = from,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light,
                color = Color(0xFF93C5FD),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BirthdayCardPreview() {
    HappyBirthdayTheme {
        BirthdayCard(message = "Happy Birthday Arthur!", from = "— Micah")
    }
}
