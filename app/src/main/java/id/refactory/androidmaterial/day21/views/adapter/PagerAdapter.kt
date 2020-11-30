package id.refactory.androidmaterial.day21.views.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.refactory.androidmaterial.day21.models.PageModel

class PagerAdapter(
    private val list: List<PageModel>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position].fragment
}