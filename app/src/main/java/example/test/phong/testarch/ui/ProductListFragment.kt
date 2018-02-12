package example.test.phong.testarch.ui


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import example.test.phong.testarch.R
import example.test.phong.testarch.databinding.FragmentProductListBinding
import example.test.phong.testarch.db.entity.ProductEntity
import example.test.phong.testarch.util.whenNull
import example.test.phong.testarch.viewmodel.ProductListViewModel


/**
 * A simple [Fragment] subclass.
 */
class ProductListFragment : Fragment() {
    lateinit var mBinding: FragmentProductListBinding
    private lateinit var mProductAdapter: ProductAdapter
    private val mProductClickCallback = object : ProductClickCallback {
        override fun onClick(product: ProductEntity) {
            // info - only allow clickable when at least the life cycle in started state
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity).show(product)
            }
        }
    }

    companion object {
        fun newInstance() = ProductListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)
        mBinding.productsList.apply {
            mProductAdapter = ProductAdapter(mProductClickCallback)
            adapter = mProductAdapter
        }
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
        subscribeUi(viewModel)
    }

    private fun subscribeUi(viewModel: ProductListViewModel) {
        // Update the list when the data changes
        viewModel.getProducts().observe(this, Observer { myProducts ->
            myProducts.whenNull {
                mBinding.isLoading = true
            }
            myProducts?.let {
                mBinding.isLoading = false
                mProductAdapter.setProductList(it)
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings()
        })
    }
}
