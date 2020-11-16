package id.refactory.androidmaterial.day11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.refactory.androidmaterial.databinding.ActivityElevenBinding

class ElevenActivity : AppCompatActivity() {

    private val binding by lazy { ActivityElevenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}