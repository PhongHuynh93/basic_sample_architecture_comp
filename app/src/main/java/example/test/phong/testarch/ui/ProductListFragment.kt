package example.test.phong.testarch.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import example.test.phong.testarch.R


/**
 * A simple [Fragment] subclass.
 */
class ProductListFragment : Fragment() {
    companion object {
        val TAG = "ProductListViewModel"
        fun newInstance() = ProductListFragment()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_product_list, container, false)
    }

}// Required empty public constructor
