package example.test.phong.testarch

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by user on 2/10/2018.
 */
class AppExecutors private constructor(var diskIO: Executor, var networkIO: Executor, var mainThread: Executor) {
    constructor(): this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), MainThreadExecutor())
}

class MainThreadExecutor: Executor {
    val mainThreadHandler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
        mainThreadHandler.post(command)
    }

}