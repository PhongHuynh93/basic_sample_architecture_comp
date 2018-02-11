package example.test.phong.testarch.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import example.test.phong.testarch.db.entity.CommentEntity

/**
 * Created by user on 2/11/2018.
 */
@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(comments: List<CommentEntity>)

    @Query("SELECT * FROM comments WHERE productId = :productId")
    fun loadComments(productId: Int): LiveData<List<CommentEntity>>
}