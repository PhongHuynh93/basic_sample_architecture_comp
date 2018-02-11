package example.test.phong.testarch.ui

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import example.test.phong.testarch.R
import example.test.phong.testarch.databinding.ProductItemBinding
import example.test.phong.testarch.db.entity.ProductEntity
import example.test.phong.testarch.util.whenNull

/**
 * Created by user on 2/11/2018.
 */
class ProductAdapter(var mProductClickCallback: ProductClickCallback?) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    var mProductList: List<ProductEntity>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = DataBindingUtil.inflate<ProductItemBinding>(LayoutInflater.from(parent.getContext()), R.layout.product_item,
                parent, false)
        binding.setCallback(mProductClickCallback)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = mProductList?.size ?: 0

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.product = mProductList?.get(position)
        holder.binding.executePendingBindings()
    }

    fun setProductList(productList: List<ProductEntity>) {
        mProductList.whenNull {
            mProductList = productList
            notifyItemRangeInserted(0, productList.size)
        }

        mProductList?.let {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return it.size
                }

                override fun getNewListSize(): Int {
                    return productList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return it.get(oldItemPosition).id == productList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newProduct = productList[newItemPosition]
                    val oldProduct = it.get(oldItemPosition)
                    // compare object because we use the equal
                    return newProduct == oldProduct
                }
            })
            mProductList = productList
            result.dispatchUpdatesTo(this)
        }
    }

    class ProductViewHolder(var binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)
}