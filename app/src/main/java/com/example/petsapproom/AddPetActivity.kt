package com.example.petsapproom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petsapproom.databinding.ActivityAddPetBinding

class AddPetActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddPetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPetBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}