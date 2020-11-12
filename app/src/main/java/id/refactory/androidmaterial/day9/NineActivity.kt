package id.refactory.androidmaterial.day9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.refactory.androidmaterial.databinding.ActivityNineBinding

class NineActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNineBinding.inflate(layoutInflater) }
    private val localStorage by lazy { LocalStorage(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        localStorage.let {
            binding.run {
                tieName.setText(it.getName())
                cbIsSingle.isChecked = it.getIsSingle()

                if (it.getAge() != -1) tieAge.setText(it.getAge().toString())
                if (it.getWeight() != -1.0f) tieWeight.setText(it.getWeight().toString())

                btnSave.setOnClickListener { _ ->
                    it.setName(tieName.text.toString())
                    it.setAge(tieAge.text.toString().toIntOrNull() ?: -1)
                    it.setWeight(tieWeight.text.toString().toFloatOrNull() ?: -1.0f)
                    it.setIsSingle(cbIsSingle.isChecked)
                }
            }
        }
    }
}