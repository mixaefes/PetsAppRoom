package com.example.petsapproom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import com.example.petsapproom.databinding.ActivityAddPetBinding

class AddPetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPetBinding
    private var gender: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPetBinding.inflate(layoutInflater)
        setupSpinner()
        setContentView(binding.root)
        binding.imageButtonAdd.setOnClickListener {
            val resultIntent = Intent()
            when {
                TextUtils.isEmpty(binding.editTextName.text) -> setResult(RESULT_CANCELED)
                TextUtils.isEmpty(binding.editTextBreed.text) -> setResult(RESULT_CANCELED)
                TextUtils.isEmpty(binding.editTextWeight.text) -> setResult(RESULT_CANCELED)
                else -> {
                    resultIntent.putExtra(EXTRA_REPLY, binding.editTextName.text)
                    resultIntent.putExtra(EXTRA_REPLY, binding.editTextBreed.text)
                    resultIntent.putExtra(EXTRA_REPLY, gender)
                    resultIntent.putExtra(EXTRA_REPLY, binding.editTextWeight.text)
                    setResult(RESULT_OK)

                }
            }
            finish()
        }
    }

    private fun setupSpinner() {
        val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinnerValues,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (parent?.getItemAtPosition(position)) {
                    R.string.gender_unknown -> gender = 0
                    R.string.gender_male -> gender = 1
                    R.string.gender_female -> gender = 2
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@AddPetActivity, "choose gender please", Toast.LENGTH_SHORT).show()
            }

        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}