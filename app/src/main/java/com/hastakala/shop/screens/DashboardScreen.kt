package com.hastakala.shop.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hastakala.shop.viewmodel.ShopViewModel

@Composable
fun DashboardScreen(viewModel: ShopViewModel) {
    val products by viewModel.allProducts.collectAsState()
    val sales by viewModel.allSales.collectAsState()
    val revenue by viewModel.totalRevenue.collectAsState()

    val lowStockCount = products.count { it.quantity < 5 }
    val totalSalesCount = sales.sumOf { it.quantity }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.surface)
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Hasta-Kala Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                StatCard("Total Sales", totalSalesCount.toString(), MaterialTheme.colorScheme.primary)
            }
            item {
                StatCard("Total Products", products.size.toString(), MaterialTheme.colorScheme.secondary)
            }
            item {
                StatCard("Revenue", "$${"%.2f".format(revenue ?: 0.0)}", MaterialTheme.colorScheme.tertiary)
            }
            item {
                StatCard(
                    "Low Stock",
                    lowStockCount.toString(),
                    if (lowStockCount > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "AI Restock Recommendations",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        val recommendations = viewModel.getRestockSuggestions(products)
        if (recommendations.isEmpty()) {
            Text("All stock levels are healthy!", modifier = Modifier.padding(top = 8.dp))
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(recommendations.size) { index ->
                    val product = recommendations[index]
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("${product.name} is low on stock (${product.quantity} left)")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, color: Color) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = color)
        }
    }
}
