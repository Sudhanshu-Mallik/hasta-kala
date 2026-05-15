package com.hastakala.shop.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hastakala.shop.database.ShopDatabase
import com.hastakala.shop.database.ShopRepository
import com.hastakala.shop.model.Product
import com.hastakala.shop.model.Sale
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShopViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ShopRepository
    val allProducts: StateFlow<List<Product>>
    val allSales: StateFlow<List<Sale>>
    val totalRevenue: StateFlow<Double?>

    init {
        val shopDao = ShopDatabase.getDatabase(application).shopDao()
        repository = ShopRepository(shopDao)
        allProducts = repository.allProducts.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        allSales = repository.allSales.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        totalRevenue = repository.totalRevenue.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

        // Add dummy data if empty
        viewModelScope.launch {
            if (repository.allProducts.first().isEmpty()) {
                addSampleData()
            }
        }
    }

    private suspend fun addSampleData() {
        val samples = listOf(
            Product(name = "Handmade Vase", category = "Decor", price = 45.0, quantity = 10),
            Product(name = "Silk Scarf", category = "Apparel", price = 30.0, quantity = 3),
            Product(name = "Wooden Coasters", category = "Kitchen", price = 15.0, quantity = 20),
            Product(name = "Ceramic Bowl", category = "Kitchen", price = 25.0, quantity = 4),
            Product(name = "Cotton Tote Bag", category = "Accessories", price = 12.0, quantity = 15)
        )
        samples.forEach { repository.insertProduct(it) }
    }

    fun addProduct(name: String, category: String, price: Double, quantity: Int) {
        viewModelScope.launch {
            repository.insertProduct(Product(name = name, category = category, price = price, quantity = quantity))
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }

    fun addSale(product: Product, quantity: Int) {
        viewModelScope.launch {
            val sale = Sale(
                productId = product.id,
                productName = product.name,
                quantity = quantity,
                totalPrice = product.price * quantity
            )
            repository.insertSale(sale)
        }
    }
    
    // AI Recommendation logic: Suggest restocking for items with quantity < 5
    fun getRestockSuggestions(products: List<Product>): List<Product> {
        return products.filter { it.quantity < 5 }
    }
}
