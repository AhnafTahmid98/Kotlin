package com.example.multicounterlazyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.multicounterlazyapp.ui.theme.MultiCounterLazyAppTheme

// Simple state holder for one counter
data class CounterItem(
    val id: Int,
    val name: String,
    val value: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiCounterLazyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MultiCounterScreen()
                }
            }
        }
    }
}

@Composable
fun MultiCounterScreen() {
    // List of counters, each with its own value
    var counters by remember {
        mutableStateOf(
            List(5) { index ->
                CounterItem(
                    id = index + 1,
                    name = "Counter_${index + 1}",
                    value = 0
                )
            }
        )
    }

    // Helper actions
    fun increment(id: Int) {
        counters = counters.map { counter ->
            if (counter.id == id) counter.copy(value = counter.value + 1) else counter
        }
    }

    fun decrement(id: Int) {
        counters = counters.map { counter ->
            if (counter.id == id) counter.copy(value = counter.value - 1) else counter
        }
    }

    fun removeCounter(id: Int) {
        counters = counters.filterNot { it.id == id }
    }

    fun addCounter() {
        val nextId = (counters.maxOfOrNull { it.id } ?: 0) + 1
        counters = counters + CounterItem(
            id = nextId,
            name = "Counter_$nextId",
            value = 0
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = "Multi Counter (LazyColumn)",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Scrollable list of counters
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = counters,
                key = { it.id }
            ) { counter ->
                CounterRow(
                    item = counter,
                    onIncrement = { increment(counter.id) },
                    onDecrement = { decrement(counter.id) },
                    onRemove = { removeCounter(counter.id) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Add new counter button
        Button(
            onClick = { addCounter() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add counter")
        }
    }
}

@Composable
fun CounterRow(
    item: CounterItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemove: () -> Unit
) {
    androidx.compose.material3.Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: name
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            // Middle: - value +
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(onClick = onDecrement) {
                    Text(text = "-")
                }

                Text(
                    text = item.value.toString(),
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedButton(onClick = onIncrement) {
                    Text(text = "+")
                }
            }

            // Right: Remove button (optional feature)
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = onRemove) {
                Text(text = "Remove")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MultiCounterPreview() {
    MultiCounterLazyAppTheme {
        MultiCounterScreen()
    }
}
