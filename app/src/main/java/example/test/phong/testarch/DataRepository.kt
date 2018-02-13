package example.test.phong.testarch

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import example.test.phong.testarch.db.AppDatabase
import example.test.phong.testarch.db.entity.CommentEntity
import example.test.phong.testarch.db.entity.ProductEntity
import example.test.phong.testarch.util.whenNull

/**
 * Created by user on 2/10/2018.
 */
class DataRepository(val database: AppDatabase) {
    private var mObservableProducts: MediatorLiveData<List<ProductEntity>>

    init {
        mObservableProducts = MediatorLiveData<List<ProductEntity>>()
        // info notify when the database changed and db is created
                // test - find out why the load all products didn't happen on bg thread but we still use postValue
        mObservableProducts.addSource(database.productDao().loadAllProducts(),
                { productEntities ->
                    database.mIsDatabaseCreated.getValue()?.let {
                        mObservableProducts.postValue(productEntities)
                    }
                })
    }

    companion object {
        private var sInstance: DataRepository? = null

        fun getInstance(database: AppDatabase): DataRepository {
            sInstance.whenNull {
                synchronized(DataRepository::class.java) {
                    sInstance.whenNull {
                        sInstance = DataRepository(database)
                    }
                }
            }
            return sInstance!!
        }
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    fun getProducts(): LiveData<List<ProductEntity>> {
        return mObservableProducts
    }

    fun loadProduct(productId: Int): LiveData<ProductEntity> {
        return database.productDao().loadProduct(productId)
    }

    fun loadComments(productId: Int): LiveData<List<CommentEntity>> {
        return database.commentDao().loadComments(productId)
    }
}