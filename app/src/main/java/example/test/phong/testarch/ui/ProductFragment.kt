package example.test.phong.testarch.ui


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import example.test.phong.testarch.R
import example.test.phong.testarch.databinding.FragmentProductBinding
import example.test.phong.testarch.db.entity.CommentEntity
import example.test.phong.testarch.db.entity.ProductEntity
import example.test.phong.testarch.util.whenNull
import example.test.phong.testarch.viewmodel.ProductViewModel


/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment() {
    companion object {
        private val KEY_PRODUCT_ID = "product_id"

        fun newInstance(productId: Int): ProductFragment = ProductFragment().apply {
            arguments = bundleOf(Pair(KEY_PRODUCT_ID, productId))
        }
    }

    private lateinit var mBinding: FragmentProductBinding

    private val mCommentClickCallback: CommentClickCallback = object : CommentClickCallback {
        override fun onClick(comment: CommentEntity) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private lateinit var mCommentAdapter: CommentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate this data binding layout
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)

        // Create and set the adapter for the RecyclerView.
        mCommentAdapter = CommentAdapter(mCommentClickCallback)
        mBinding.commentList.setAdapter(mCommentAdapter)
        return mBinding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ProductViewModel.Factory(activity!!.application, arguments!!.getInt(KEY_PRODUCT_ID))
        val model = ViewModelProviders.of(this, factory).get(ProductViewModel::class.java)
        mBinding.productViewModel = model
        subcribeToModel(model)
    }

    private fun subcribeToModel(model: ProductViewModel) {
        // Observe product data
        model.mObservableProduct.observe(this, Observer<ProductEntity> {
            it?.let {
                model.setProduct(it)
            }
        })

        // Observe comments
        model.mObservableComments.observe(this, Observer<List<CommentEntity>> {
            it?.let {
                mBinding.isLoading = false
                mCommentAdapter.setCommentList(it)
            }

            it.whenNull {
                mBinding.isLoading = true
            }
        })
    }
}
