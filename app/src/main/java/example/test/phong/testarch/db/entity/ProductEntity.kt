package example.test.phong.testarch.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by user on 2/11/2018.
 */
@Entity(tableName = "products")
data class ProductEntity(@PrimaryKey var id: Int, var name: String, var description: String, var price: Int)