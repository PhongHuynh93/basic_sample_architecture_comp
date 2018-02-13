package example.test.phong.testarch.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import example.test.phong.testarch.db.entity.ProductEntity

/**
 * Created by user on 2/11/2018.
 */
@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun loadAllProducts(): LiveData<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM products WHERE id = :productId")
    fun loadProduct(productId: Int): LiveData<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :productId")
    fun loadProductSync(productId: Int): ProductEntity
}