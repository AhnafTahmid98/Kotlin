package com.example.kotlincounterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlincounterapp.ui.theme.KotlinCounterappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinCounterappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ThreeCountersScreen()
                }
            }
        }
    }
}

@Composable
fun ThreeCountersScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Counter App",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        // Three independent counters
        CounterRow(label = "Counter 1")
        CounterRow(label = "Counter 2")
        CounterRow(label = "Counter 3")
    }
}

@Composable
fun CounterRow(label: String) {
    var textValue by remember { mutableStateOf("0") }

    fun currentValue(): Int = textValue.toIntOrNull() ?: 0

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                val newValue = currentValue() - 1
                textValue = newValue.toString()
            }) {
                Text(text = "-")
            }

            OutlinedTextField(
                value = textValue,
                onValueChange = { newText ->
                    // allow empty or numeric only
                    if (newText.isEmpty() || newText.toIntOrNull() != null) {
                        textValue = newText
                    }
                },
                singleLine = true,
                modifier = Modifier.width(120.dp),
                label = { Text("Start / value") }
            )

            Button(onClick = {
                val newValue = currentValue() + 1
                textValue = newValue.toString()
            }) {
                Text(text = "+")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ThreeCountersPreview() {
    KotlinCounterappTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ThreeCountersScreen()
        }
    }
}
