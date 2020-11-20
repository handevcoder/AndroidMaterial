package id.refactory.androidmaterial.day15

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import id.refactory.androidmaterial.databinding.ActivityFifteenBinding
import id.refactory.androidmaterial.day15.fragments.OneFragment
import id.refactory.androidmaterial.day15.fragments.TwoFragment

class FifteenActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFifteenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        val pagers = listOf(
            Pager("One", OneFragment()),
            Pager("Two", TwoFragment()),
            Pager("Three", OneFragment()),
            Pager("Four", TwoFragment()),
            Pager("One", OneFragment()),
            Pager("Two", TwoFragment()),
            Pager("Three", OneFragment()),
            Pager("Four", TwoFragment())
        )

        val pagerAdapter = MainPagerAdapter(pagers, supportFragmentManager, lifecycle)

        binding.run {
            vpPager.adapter = pagerAdapter

            TabLayoutMediator(tlTabs, vpPager) { tab, index -> tab.text = pagers[index].title }.attach()
        }
    }

    data class Pager(val title: String, val fragment: Fragment)

    class MainPagerAdapter(
        private val list: List<Pager>,
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle
    ) : FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int = list.size

        override fun createFragment(position: Int): Fragment = list[position].fragment
    }
}