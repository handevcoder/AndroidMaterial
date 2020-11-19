package id.refactory.androidmaterial.day14

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.refactory.androidmaterial.databinding.ActivityFourteenBinding

class FourteenActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFourteenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}