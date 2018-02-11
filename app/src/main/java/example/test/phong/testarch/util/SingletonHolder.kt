package example.test.phong.testarch.util

/**
 * Created by user on 2/11/2018.
 * like the object in kotlin, only used this method when we create singleton object but need argument
 *
 * <a href="https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e"></a>
 */
open class SingletonHolder<out T, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}