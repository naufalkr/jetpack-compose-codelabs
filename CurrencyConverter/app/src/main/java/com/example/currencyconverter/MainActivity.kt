package com.example.currencyconverter

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
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.filled.ArrowDropDown

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterApp()
        }
    }
}

@Composable
fun CurrencyConverterApp() {
    val currencies = listOf("USD", "IDR", "EUR", "GBP", "JPY", "SGD")
    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("USD") }
    var toCurrency by remember { mutableStateOf("IDR") }
    var result by remember { mutableStateOf("Result: ") }

    val exchangeRates = mapOf(
        "USD" to mapOf(
            "IDR" to 16833.10,
            "EUR" to 0.807,
            "GBP" to 0.719,
            "JPY" to 150.85,
            "SGD" to 1.32
        ),
        "IDR" to mapOf(
            "USD" to 0.0000594,
            "EUR" to 0.0000479,
            "GBP" to 0.0000427,
            "JPY" to 0.00896,
            "SGD" to 0.0000784
        ),
        "EUR" to mapOf(
            "USD" to 1.24,
            "IDR" to 20860.35,
            "GBP" to 0.891,
            "JPY" to 187.00,
            "SGD" to 1.64
        ),
        "GBP" to mapOf(
            "USD" to 1.39,
            "IDR" to 23400.14,
            "EUR" to 1.12,
            "JPY" to 209.80,
            "SGD" to 1.84
        ),
        "JPY" to mapOf(
            "USD" to 0.00663,
            "IDR" to 111.62,
            "EUR" to 0.00535,
            "GBP" to 0.00477,
            "SGD" to 0.00875
        ),
        "SGD" to mapOf(
            "USD" to 0.758,
            "IDR" to 12752.35,
            "EUR" to 0.610,
            "GBP" to 0.544,
            "JPY" to 114.28
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Currency Converter",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2652A0),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF2652A0),
                    unfocusedIndicatorColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("From", color = Color(0xFF2652A0))
                    Spacer(modifier = Modifier.height(4.dp))
                    DropdownMenuCurrency(
                        selectedCurrency = fromCurrency,
                        currencies = currencies,
                        onCurrencySelected = { fromCurrency = it }
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("To", color = Color(0xFF2652A0))
                    Spacer(modifier = Modifier.height(4.dp))
                    DropdownMenuCurrency(
                        selectedCurrency = toCurrency,
                        currencies = currencies,
                        onCurrencySelected = { toCurrency = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (amount.isBlank()) {
                        result = "Please enter amount"
                        return@Button
                    }
                    val amountValue = amount.toDoubleOrNull()
                    if (amountValue == null) {
                        result = "Invalid amount"
                        return@Button
                    }

                    if (fromCurrency == toCurrency) {
                        result = "Result: $amountValue $toCurrency"
                        return@Button
                    }

                    val rate = exchangeRates[fromCurrency]?.get(toCurrency)
                    if (rate == null) {
                        result = "Conversion rate not available"
                        return@Button
                    }

                    val convertedAmount = amountValue * rate
                    result = "Result: ${"%.2f".format(convertedAmount)} $toCurrency"
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2652A0),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text("Convert", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = result,
                fontSize = 20.sp,
                color = Color(0xFF2652A0),
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .background(Color(0xFFE8F0FE), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun DropdownMenuCurrency(
    selectedCurrency: String,
    currencies: List<String>,
    onCurrencySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded = true },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF2652A0)
            ),
            border = BorderStroke(1.dp, Color.LightGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                selectedCurrency,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Start
            )
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(currency) },
                    onClick = {
                        onCurrencySelected(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}