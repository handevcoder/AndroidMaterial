package id.refactory.androidmaterial.day18.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.refactory.androidmaterial.R

class EighteenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eighteen)

        supportActionBar?.hide()
    }
}