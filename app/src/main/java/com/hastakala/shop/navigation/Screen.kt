package com.hastakala.shop.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Default.Dashboard)
    object Products : Screen("products", "Products", Icons.Default.ShoppingBag)
    object AddSale : Screen("add_sale", "Add Sale", Icons.Default.AddShoppingCart)
    object Analytics : Screen("analytics", "Analytics", Icons.Default.BarChart)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}
