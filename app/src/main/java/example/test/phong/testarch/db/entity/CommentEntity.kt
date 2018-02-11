package example.test.phong.testarch.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import example.test.phong.testarch.model.Comment
import java.util.*


/**
 * Created by user on 2/11/2018.
 */
@Entity(tableName = "comments",
        foreignKeys = arrayOf(ForeignKey(entity = ProductEntity::class, parentColumns = arrayOf("id"), childColumns = arrayOf("productId"), onDelete = ForeignKey.CASCADE)),
        indices = arrayOf(Index(value = "productId")))
class CommentEntity constructor(var productId: Int, var text: String, var postedAt: Date) : Comment {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}