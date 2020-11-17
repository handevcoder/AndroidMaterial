package id.refactory.androidmaterial.day12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import id.refactory.androidmaterial.R
import id.refactory.androidmaterial.databinding.ActivityTwelveBinding
import id.refactory.androidmaterial.day12.fragments.ConstraintFragment
import id.refactory.androidmaterial.day12.fragments.LinearFragment
import id.refactory.androidmaterial.day12.fragments.RelativeFragment

class TwelveActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTwelveBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showFragment(RelativeFragment())

        binding.run {
            bnvLayout.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.relative_menu -> {
                        showFragment(RelativeFragment())

                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.linear_menu -> {
                        showFragment(LinearFragment())

                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.constraint_menu -> {
                        showFragment(ConstraintFragment())

                        return@setOnNavigationItemSelectedListener true
                    }
                }

                false
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_layout, fragment).commit()
    }
}