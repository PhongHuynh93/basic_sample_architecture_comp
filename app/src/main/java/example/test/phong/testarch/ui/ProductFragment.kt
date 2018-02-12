package example.test.phong.testarch.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import example.test.phong.testarch.R


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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                     savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

}
