package com.example.shoppinglistroomapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ShoppingUiState(
    val items: List<ShoppingItem> = emptyList(),
    val editingItem: ShoppingItem? = null
)

class ShoppingViewModel(
    private val dao: ShoppingItemDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingUiState())
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dao.getAllItems().collect { items ->
                _uiState.update { it.copy(items = items) }
            }
        }
    }

    fun addItem(name: String, quantity: String, unit: String, price: String) {
        val qty = quantity.toIntOrNull() ?: return
        val p = price.toDoubleOrNull() ?: return
        viewModelScope.launch {
            dao.insertItem(
                ShoppingItem(
                    name = name,
                    quantity = qty,
                    unit = unit,
                    price = p
                )
            )
        }
    }

    fun startEdit(item: ShoppingItem) {
        _uiState.update { it.copy(editingItem = item) }
    }

    fun saveEdit(name: String, quantity: String, unit: String, price: String) {
        val editing = _uiState.value.editingItem ?: return
        val qty = quantity.toIntOrNull() ?: return
        val p = price.toDoubleOrNull() ?: return
        viewModelScope.launch {
            dao.updateItem(
                editing.copy(
                    name = name,
                    quantity = qty,
                    unit = unit,
                    price = p
                )
            )
            _uiState.update { it.copy(editingItem = null) }
        }
    }

    fun cancelEdit() {
        _uiState.update { it.copy(editingItem = null) }
    }

    fun deleteItem(item: ShoppingItem) {
        viewModelScope.launch {
            dao.deleteItem(item)
        }
    }
}
