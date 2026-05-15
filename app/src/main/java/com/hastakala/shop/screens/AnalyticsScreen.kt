package com.hastakala.shop.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hastakala.shop.viewmodel.ShopViewModel

@Composable
fun AnalyticsScreen(viewModel: ShopViewModel) {
    val sales by viewModel.allSales.collectAsState()
    
    // Simple data processing for "charts"
    val productSales = sales.groupBy { it.productName }
        .mapValues { it.value.sumOf { sale -> sale.quantity } }
        .toList()
        .sortedByDescending { it.second }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text(
            text = "Sales Analytics",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text("Best Selling Products", style = MaterialTheme.typography.titleLarge)
        
        Spacer(modifier = Modifier.height(16.dp))

        // Simple bar chart representation
        productSales.take(5).forEach { (name, qty) ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(text = name, style = MaterialTheme.typography.bodyMedium)
                LinearProgressIndicator(
                    progress = { (qty.toFloat() / (productSales.firstOrNull()?.second ?: 1).toFloat()) },
                    modifier = Modifier.fillMaxWidth().height(12.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Text(text = "$qty units", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 4.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Recent Sales History", style = MaterialTheme.typography.titleLarge)
        
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(sales.reversed()) { sale ->
                ListItem(
                    headlineContent = { Text(sale.productName) },
                    supportingContent = { Text("Quantity: ${sale.quantity} | Total: $${"%.2f".format(sale.totalPrice)}") },
                    trailingContent = { Text(java.text.SimpleDateFormat("MMM dd, HH:mm").format(java.util.Date(sale.timestamp))) }
                )
            }
        }
    }
}
