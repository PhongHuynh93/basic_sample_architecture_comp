package example.test.phong.testarch.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import example.test.phong.testarch.BasicApp
import example.test.phong.testarch.DataRepository
import example.test.phong.testarch.db.entity.CommentEntity
import example.test.phong.testarch.db.entity.ProductEntity

/**
 * Created by user on 2/13/2018.
 */
class ProductViewModel(application: Application, repository: DataRepository, val productId: Int): AndroidViewModel(application) {
    var mObservableComments: LiveData<List<CommentEntity>>
    var mObservableProduct: LiveData<ProductEntity>

    init {
        mObservableComments = repository.loadComments(productId)
        mObservableProduct = repository.loadProduct(productId)
    }

    /**
     * A creator is used to inject the product ID into the ViewModel
     *
     *
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    class Factory(private val mApplication: Application, private val mProductId: Int) : ViewModelProvider.NewInstanceFactory() {

        private val mRepository: DataRepository

        init {
            mRepository = (mApplication as BasicApp).getRepository()
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return ProductViewModel(mApplication, mRepository, mProductId) as T
        }
    }
}