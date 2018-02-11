package example.test.phong.testarch

import android.app.Application
import example.test.phong.testarch.db.AppDatabase

/**
 * Created by user on 2/10/2018.
 */
class BasicApp: Application() {
    private lateinit var mAppExecutors: AppExecutors

    override fun onCreate() {
        super.onCreate()
        mAppExecutors = AppExecutors()
    }

    fun getDatabase(): AppDatabase {
        return AppDatabase.getInstance(this, mAppExecutors)
    }

    fun getRepository(): DataRepository {
        return DataRepository.getInstance(getDatabase())
    }
}