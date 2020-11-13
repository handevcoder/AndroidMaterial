package id.refactory.androidmaterial.day10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import id.refactory.androidmaterial.R
import id.refactory.androidmaterial.databinding.ActivityTenBinding
import id.refactory.androidmaterial.day10.fragments.FirstFragment
import id.refactory.androidmaterial.day10.fragments.SecondFragment
import id.refactory.androidmaterial.day10.fragments.ThirdFragment
import kotlinx.android.synthetic.main.activity_ten.*

class TenActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            btnFragmentOne.setOnClickListener { changeFragment(FirstFragment()) }
            btnFragmentTwo.setOnClickListener { changeFragment(SecondFragment()) }
            btnFragmentThree.setOnClickListener { changeFragment(ThirdFragment()) }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_main, fragment).commit()
    }
}