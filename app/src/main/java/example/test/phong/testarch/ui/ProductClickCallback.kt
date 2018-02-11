package example.test.phong.testarch.ui

import example.test.phong.testarch.db.entity.ProductEntity

/**
 * Created by user on 2/11/2018.
 */
interface ProductClickCallback {
    fun onClick(product: ProductEntity)
}