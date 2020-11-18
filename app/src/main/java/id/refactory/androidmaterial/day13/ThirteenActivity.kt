package id.refactory.androidmaterial.day13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.refactory.androidmaterial.databinding.ActivityThirteenBinding

class ThirteenActivity : AppCompatActivity() {

    private val binding by lazy { ActivityThirteenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}