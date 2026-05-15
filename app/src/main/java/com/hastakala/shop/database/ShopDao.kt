package com.hastakala.shop.database

import androidx.room.*
import com.hastakala.shop.model.Product
import com.hastakala.shop.model.Sale
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao {
    // Product operations
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): Product?

    // Sale operations
    @Query("SELECT * FROM sales")
    fun getAllSales(): Flow<List<Sale>>

    @Insert
    suspend fun insertSale(sale: Sale)

    @Query("SELECT SUM(totalPrice) FROM sales")
    fun getTotalRevenue(): Flow<Double?>
}
