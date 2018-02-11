package example.test.phong.testarch.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import example.test.phong.testarch.R
import example.test.phong.testarch.db.entity.ProductEntity
import example.test.phong.testarch.util.addFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add product list fragment if this is first creation
        addFragment(ProductListFragment.newInstance(), ProductListFragment.TAG, R.id.fragment_container)
    }

    fun show(product: ProductEntity) {
//        replaceFragmentSafely()
    }
}
