package com.example.mycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var num1 by remember {
        mutableStateOf("")
    }
    var num2 by remember {
        mutableStateOf("")
    }
    var result by remember {
        mutableStateOf("Result: ")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 140.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Calculator",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2652A0),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = num1,
                onValueChange = { num1 = it },
                label = { Text("Input 1") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF2652A0),
                    unfocusedIndicatorColor = Color.LightGray
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = num2,
                onValueChange = { num2 = it },
                label = { Text("Input 2") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF2652A0),
                    unfocusedIndicatorColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculatorButton("+") { calculate(num1, num2, '+') { result = it } }
                CalculatorButton("-") { calculate(num1, num2, '-') { result = it } }
                CalculatorButton("×") { calculate(num1, num2, '*') { result = it } }
                CalculatorButton("÷") { calculate(num1, num2, '/') { result = it } }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = result,
                fontSize = 20.sp,
                color = Color(0xFF2652A0),
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .background(Color(0xFFE8F0FE), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun CalculatorButton(symbol: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2652A0),
            contentColor = Color.White
        ),
        modifier = Modifier.size(60.dp)
    ) {
        Text(text = symbol, fontSize = 20.sp)
    }
}

fun calculate(num1: String, num2: String, operation: Char, updateResult: (String) -> Unit) {
    val n1 = num1.toDoubleOrNull()
    val n2 = num2.toDoubleOrNull()

    if (n1 == null || n2 == null) {
        updateResult("Invalid input!")
        return
    }

    val res = when (operation) {
        '+' -> n1 + n2
        '-' -> n1 - n2
        '*' -> n1 * n2
        '/' -> if (n2 != 0.0) n1 / n2 else "Cannot divide by zero"
        else -> "Unknown operation"
    }
    updateResult("Result: $res")
}