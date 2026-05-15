package com.hastakala.shop.database

import com.hastakala.shop.model.Product
import com.hastakala.shop.model.Sale
import kotlinx.coroutines.flow.Flow

class ShopRepository(private val shopDao: ShopDao) {
    val allProducts: Flow<List<Product>> = shopDao.getAllProducts()
    val allSales: Flow<List<Sale>> = shopDao.getAllSales()
    val totalRevenue: Flow<Double?> = shopDao.getTotalRevenue()

    suspend fun insertProduct(product: Product) {
        shopDao.insertProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        shopDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        shopDao.deleteProduct(product)
    }

    suspend fun insertSale(sale: Sale) {
        shopDao.insertSale(sale)
        // Also update product quantity
        val product = shopDao.getProductById(sale.productId)
        if (product != null) {
            val updatedProduct = product.copy(quantity = product.quantity - sale.quantity)
            shopDao.updateProduct(updatedProduct)
        }
    }
}
