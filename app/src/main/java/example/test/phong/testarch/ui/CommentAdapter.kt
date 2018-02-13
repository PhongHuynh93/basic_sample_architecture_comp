package example.test.phong.testarch.ui

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import example.test.phong.testarch.R
import example.test.phong.testarch.databinding.CommentItemBinding
import example.test.phong.testarch.db.entity.CommentEntity
import example.test.phong.testarch.util.whenNull

/**
 * Created by user on 2/13/2018.
 */
class CommentAdapter(val mCommentClickCallback: CommentClickCallback?) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    private var mCommentList: List<CommentEntity>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = DataBindingUtil.inflate<CommentItemBinding>(LayoutInflater.from(parent.getContext()), R.layout.comment_item,
                parent, false)
        binding.setCallback(mCommentClickCallback)
        return CommentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mCommentList?.size ?: 0
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.binding.comment = mCommentList?.get(position)
        holder.binding.executePendingBindings()
    }

    fun setCommentList(comments: List<CommentEntity>) {
        mCommentList.whenNull {
            mCommentList = comments
            notifyItemRangeInserted(0, mCommentList!!.size)
        }

        mCommentList?.let {
            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return it.get(oldItemPosition).id == comments.get(newItemPosition).id
                }

                override fun getOldListSize(): Int {
                    return it.size
                }

                override fun getNewListSize(): Int {
                    return comments.size
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return it.get(oldItemPosition) == comments.get(newItemPosition)
                }
            })
        }
    }

    class CommentViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root)
}