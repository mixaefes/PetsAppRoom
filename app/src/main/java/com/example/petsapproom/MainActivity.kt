package com.example.petsapproom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.petsapproom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    Toast.makeText(this, "thanks for adding information", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "you aren't add information", Toast.LENGTH_SHORT).show()
                }
            }
        binding.floatingActionButton.setOnClickListener {
            startAddActivity()
        }
    }

    private fun startAddActivity() {
        val intent = Intent(this, AddPetActivity::class.java)
        resultLauncher.launch(intent)
    }
}