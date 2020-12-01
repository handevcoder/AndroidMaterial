package id.refactory.androidmaterial.day22.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.refactory.androidmaterial.R

class TwentyTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty_two)

        supportActionBar?.hide()
    }
}