package id.refactory.androidmaterial.day7

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.refactory.androidmaterial.R
import id.refactory.androidmaterial.databinding.ActivitySevenBinding

enum class State {
    ADD, SUBTRACT, MULTIPLE, DIVIDE
}

class SevenActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySevenBinding.inflate(layoutInflater) }
    private var state = State.ADD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            fabAction.setOnClickListener { calculate() }
            btnClear.setOnClickListener { clearValue() }
        }
    }

    private fun clearValue() {
        val value = binding.tvValue.text.toString().toIntOrNull() ?: 0

        if (value != 0) {
            MaterialAlertDialogBuilder(this).run {
                setMessage("Apakah Anda yakin ingin menghapus nilai?")
                setPositiveButton("Ya") { dialog, _ ->
                    binding.tvValue.text = "0"
                    dialog.dismiss()
                }
                setNegativeButton("Tidak") { dialog, _ -> dialog.dismiss() }
                show()
            }
        } else {
            Toast.makeText(this, "Value sudah kosong!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                state = State.ADD

                binding.fabAction.setImageResource(R.drawable.ic_add)

                true
            }
            R.id.subtract -> {
                state = State.SUBTRACT

                binding.fabAction.setImageResource(R.drawable.ic_subtract)

                true
            }
            R.id.multiple -> {
                state = State.MULTIPLE

                binding.fabAction.setImageResource(R.drawable.ic_multiple)

                true
            }
            R.id.divide -> {
                state = State.DIVIDE

                binding.fabAction.setImageResource(R.drawable.ic_divide)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun calculate() {
        var value = binding.tvValue.text.toString().toIntOrNull() ?: 0

        when (state) {
            State.ADD -> value++
            State.SUBTRACT -> value--
            State.MULTIPLE -> value *= 2
            State.DIVIDE -> value /= 2
        }

        binding.tvValue.text = value.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.basic_menu, menu)

        return true
    }
}