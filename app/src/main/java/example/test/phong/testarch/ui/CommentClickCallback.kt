package example.test.phong.testarch.ui

import example.test.phong.testarch.db.entity.CommentEntity

/**
 * Created by user on 2/13/2018.
 */
interface CommentClickCallback {
    fun onClick(comment: CommentEntity)
}