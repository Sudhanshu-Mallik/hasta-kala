package com.hastakala.shop.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hastakala.shop.viewmodel.ShopViewModel
import com.hastakala.shop.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSaleScreen(viewModel: ShopViewModel) {
    val products by viewModel.allProducts.collectAsState()
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var quantity by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val totalPrice = (selectedProduct?.price ?: 0.0) * (quantity.toIntOrNull() ?: 0)

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text(
            text = "Record New Sale",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Modern Material 3 Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedProduct?.name ?: "Select Product",
                onValueChange = {},
                readOnly = true,
                label = { Text("Product") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true).fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                products.forEach { product ->
                    DropdownMenuItem(
                        text = { Text("${product.name} ($${product.price}) - Stock: ${product.quantity}") },
                        onClick = {
                            selectedProduct = product
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Summary", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Unit Price:")
                    Text("$${selectedProduct?.price ?: 0.0}")
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total Amount:")
                    Text("$${"%.2f".format(totalPrice)}", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                selectedProduct?.let { product ->
                    val qty = quantity.toIntOrNull() ?: 0
                    if (qty > 0 && qty <= product.quantity) {
                        viewModel.addSale(product, qty)
                        quantity = ""
                        selectedProduct = null
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedProduct != null && quantity.isNotEmpty() && (quantity.toIntOrNull() ?: 0) <= (selectedProduct?.quantity ?: 0)
        ) {
            Text("Complete Sale")
        }
    }
}
