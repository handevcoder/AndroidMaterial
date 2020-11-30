package id.refactory.androidmaterial.day21.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import id.refactory.androidmaterial.databinding.ActivityTwentyOneBinding
import id.refactory.androidmaterial.day21.models.PageModel
import id.refactory.androidmaterial.day21.views.adapter.PagerAdapter
import id.refactory.androidmaterial.day21.views.fragments.FavoritesFragment
import id.refactory.androidmaterial.day21.views.fragments.TodosFragment

class TwentyOneActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTwentyOneBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        val pager = listOf(
            PageModel("Todo", TodosFragment()), PageModel("Favorites", FavoritesFragment())
        )

        val adapter = PagerAdapter(pager, supportFragmentManager, lifecycle)

        binding.run {
            vpPager.adapter = adapter

            TabLayoutMediator(tlTab, vpPager) { tab, index -> tab.text = pager[index].title }
        }
    }
}