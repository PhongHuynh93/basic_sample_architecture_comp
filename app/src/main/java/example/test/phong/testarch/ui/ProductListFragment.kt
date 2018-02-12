package example.test.phong.testarch.ui


import android.arch.lifecycle.Lifecycle
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import example.test.phong.testarch.R
import example.test.phong.testarch.databinding.FragmentProductListBinding
import example.test.phong.testarch.db.entity.ProductEntity


/**
 * A simple [Fragment] subclass.
 */
class ProductListFragment : Fragment() {
    lateinit var mBinding: FragmentProductListBinding
    private val mProductClickCallback = object : ProductClickCallback {
        override fun onClick(product: ProductEntity) {
            // info - only allow clickable when at least the life cycle in started state
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity).show(product)
            }
        }
    }

    companion object {
        val TAG = "ProductListViewModel"
        fun newInstance() = ProductListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)
        mBinding.productsList.apply {
            adapter = ProductAdapter(mProductClickCallback)
        }
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}
