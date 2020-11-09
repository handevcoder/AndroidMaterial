package id.refactory.androidmaterial.day6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import id.refactory.androidmaterial.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvText by lazy { findViewById<TextView>(R.id.tvText) }
        tvText.setOnClickListener { nextActivity() }
    }

    private fun nextActivity() {
        val intent = Intent(applicationContext, SecondActivity::class.java)
        startActivity(intent)
    }
}