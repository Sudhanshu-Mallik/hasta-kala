package com.hastakala.shop.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hastakala.shop.components.ProductItem
import com.hastakala.shop.viewmodel.ShopViewModel
import com.hastakala.shop.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(viewModel: ShopViewModel) {
    val products by viewModel.allProducts.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var productToEdit by remember { mutableStateOf<Product?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text(
                text = "Product Management",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                items(products) { product ->
                    ProductItem(
                        product = product,
                        onEdit = { productToEdit = product },
                        onDelete = { viewModel.deleteProduct(product) }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        ProductDialog(
            onDismiss = { showAddDialog = false },
            onSave = { name, cat, price, qty ->
                viewModel.addProduct(name, cat, price, qty)
                showAddDialog = false
            }
        )
    }

    productToEdit?.let { product ->
        ProductDialog(
            product = product,
            onDismiss = { productToEdit = null },
            onSave = { name, cat, price, qty ->
                viewModel.updateProduct(product.copy(name = name, category = cat, price = price, quantity = qty))
                productToEdit = null
            }
        )
    }
}

@Composable
fun ProductDialog(
    product: Product? = null,
    onDismiss: () -> Unit,
    onSave: (String, String, Double, Int) -> Unit
) {
    var name by remember { mutableStateOf(product?.name ?: "") }
    var category by remember { mutableStateOf(product?.category ?: "") }
    var price by remember { mutableStateOf(product?.price?.toString() ?: "") }
    var quantity by remember { mutableStateOf(product?.quantity?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (product == null) "Add Product" else "Edit Product") },
        text = {
            Column {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") })
                OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price") })
                OutlinedTextField(value = quantity, onValueChange = { quantity = it }, label = { Text("Quantity") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(name, category, price.toDoubleOrNull() ?: 0.0, quantity.toIntOrNull() ?: 0)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
