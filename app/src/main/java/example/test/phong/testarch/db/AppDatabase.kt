package example.test.phong.testarch.db

import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.support.annotation.VisibleForTesting
import example.test.phong.testarch.AppExecutors
import example.test.phong.testarch.db.dao.CommentDao
import example.test.phong.testarch.db.dao.ProductDao
import example.test.phong.testarch.db.entity.CommentEntity
import example.test.phong.testarch.db.entity.ProductEntity
import example.test.phong.testarch.util.whenNull

/**
 * Created by user on 2/11/2018.
 */
@Database(entities = arrayOf(ProductEntity::class, CommentEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    var mIsDatabaseCreated = MutableLiveData<Boolean>()

    companion object {
        @VisibleForTesting
        val DATABASE_NAME = "basic-sample-db"

        lateinit var sInstance: AppDatabase
        fun getInstance(context: Context, executors: AppExecutors): AppDatabase {
            sInstance.whenNull {
                synchronized(AppDatabase::class.java) {
                    sInstance.whenNull {
                        sInstance = buildDatabase(context.applicationContext, executors)
                        sInstance.updateDatabaseCreated(context.applicationContext)
                    }
                }
            }
            return sInstance
        }

        private fun buildDatabase(appContext: Context, executors: AppExecutors): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(
                            object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    // info - insert in another thread
                                    executors.diskIO.execute({
                                        // when creating done, add some example db
                                        addDelay()

                                        val database = AppDatabase.getInstance(appContext, executors)
                                        val products = DataGenerator.generateProducts()
                                        val comments = DataGenerator.generateCommentsForProducts(products)

                                        insertData(database, products, comments)
                                        // notify that the database was created and it's ready to be used
                                        database.setDatabaseCreated()
                                    })
                                }
                            }
                    )
                    .build()
        }

        private fun insertData(database: AppDatabase, products: List<ProductEntity>, comments: List<CommentEntity>) {
            database.runInTransaction({
                database.productDao().insertAll(products)
                database.commentDao().insertAll(comments)
            })
        }

        private fun addDelay() {
            try {
                Thread.sleep(4000)
            } catch (ignored: InterruptedException) {
            }
        }
    }

    abstract fun commentDao(): CommentDao

    abstract fun productDao(): ProductDao

    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }

    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }
}