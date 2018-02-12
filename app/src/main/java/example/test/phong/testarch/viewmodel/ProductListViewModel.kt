package example.test.phong.testarch.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import example.test.phong.testarch.BasicApp
import example.test.phong.testarch.db.entity.ProductEntity

/**
 * Created by user on 2/12/2018.
 */
class ProductListViewModel(application: Application): AndroidViewModel(application) {
    val mObservableProducts = MediatorLiveData<List<ProductEntity>>()

    init {
        // set by default null, until we get data from the database.
        mObservableProducts.value = null

        val products = (application as BasicApp).getRepository().getProducts()
        mObservableProducts.addSource(products, mObservableProducts::setValue)
    }

    fun getProducts() : LiveData<List<ProductEntity>> = mObservableProducts
}