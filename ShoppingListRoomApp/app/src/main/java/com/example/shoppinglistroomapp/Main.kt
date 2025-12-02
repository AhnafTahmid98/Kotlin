package com.example.shoppinglistroomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shoppinglistroomapp.ui.theme.ShoppingListRoomAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ShoppingViewModel by viewModels {
        val db = ShoppingDatabase.getInstance(applicationContext)
        ShoppingViewModelFactory(db.shoppingItemDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListRoomAppTheme {
                ShoppingApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingApp(viewModel: ShoppingViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    LaunchedEffect(uiState.editingItem) {
        val e = uiState.editingItem
        if (e != null) {
            name = e.name
            quantity = e.quantity.toString()
            unit = e.unit
            price = e.price.toString()
        } else {
            name = ""
            quantity = ""
            unit = ""
            price = ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping List (Room + Compose)") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // ---- Form ----
            Text(
                text = if (uiState.editingItem == null) "Add item" else "Edit item",
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Qty") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = unit,
                    onValueChange = { unit = it },
                    label = { Text("Unit") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(8.dp))

            Row {
                Button(
                    onClick = {
                        if (uiState.editingItem == null) {
                            viewModel.addItem(name, quantity, unit, price)
                        } else {
                            viewModel.saveEdit(name, quantity, unit, price)
                        }
                    }
                ) {
                    Text(if (uiState.editingItem == null) "Add" else "Save")
                }

                if (uiState.editingItem != null) {
                    Spacer(Modifier.width(8.dp))
                    OutlinedButton(onClick = { viewModel.cancelEdit() }) {
                        Text("Cancel")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ---- Table header ----
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Name", fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f))
                Text("Qty", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text("Unit", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text("Price", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text("Actions", fontWeight = FontWeight.Bold) // no weight, free width
            }

            HorizontalDivider(Modifier.padding(vertical = 4.dp))

            // ---- Table rows ----
            LazyColumn {
                items(uiState.items) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item.name, modifier = Modifier.weight(2f))
                        Text(item.quantity.toString(), modifier = Modifier.weight(1f))
                        Text(item.unit, modifier = Modifier.weight(1f))
                        Text(item.price.toString(), modifier = Modifier.weight(1f))

                        // Actions: no weight, just natural width
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Edit",
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.clickable {
                                    viewModel.startEdit(item)
                                }
                            )
                            Text(
                                text = "Delete",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.clickable {
                                    viewModel.deleteItem(item)
                                }
                            )
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}
