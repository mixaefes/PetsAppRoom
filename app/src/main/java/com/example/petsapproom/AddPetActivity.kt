package com.example.petsapproom

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.preference.PreferenceManager
import com.example.petsapproom.data.cursor.PetOpenHelper
import com.example.petsapproom.data.room.EntityPet
import com.example.petsapproom.databinding.ActivityAddPetBinding
import com.example.petsapproom.model.PetViewModelFactory
import com.example.petsapproom.model.PetsViewModel

class AddPetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPetBinding
    private var gender: Int = 0
    private val resultIntent = Intent()


    private val petViewModel: PetsViewModel by viewModels {
        PetViewModelFactory(
            (application as PetsApplication).repository,
            PreferenceManager.getDefaultSharedPreferences(application.applicationContext),
            application.applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPetBinding.inflate(layoutInflater)
        setupSpinner()
        setContentView(binding.root)
        binding.buttonDelete.isEnabled = false

        val extraId = intent.getIntExtra("id_key", 0)
        petViewModel.getPet(extraId).observe(this,{

            pet-> pet?.let{
            binding.buttonDelete.isEnabled = true
            binding.buttonDelete.setOnClickListener {
                petViewModel.deletePet(pet)
                finish()
            }
                binding.editTextName.setText(it.name)
            binding.editTextBreed.setText(it.breed)
            binding.editTextWeight.setText(it.weight.toString())
            binding.spinner.setSelection(it.gender)
        }
        })



        binding.imageButtonAdd.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.editTextName.text) -> setResult(RESULT_CANCELED)
                TextUtils.isEmpty(binding.editTextBreed.text) -> setResult(RESULT_CANCELED)
                TextUtils.isEmpty(binding.editTextWeight.text) -> setResult(RESULT_CANCELED)
                else -> setResult(RESULT_OK)
            }

            val name = binding.editTextName.text.toString()
            val breed = binding.editTextBreed.text.toString()
            val weight = Integer.parseInt(binding.editTextWeight.text.toString())
            val newPet = EntityPet(name = name, breed = breed, gender = gender, weight = weight)
            if (extraId != 0) {
                newPet.id = extraId
                petViewModel.updatePet(newPet)
            } else {
                petViewModel.insertPet(newPet)
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
                    getText(R.string.gender_unknown) -> gender = 0
                    getText(R.string.gender_male) -> gender = 1
                    getText(R.string.gender_female) -> gender = 2
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@AddPetActivity, "choose gender please", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    companion object {
        const val EXTRA_REPLY_NAME = "NAME"
        const val EXTRA_REPLY_BREED = "BREED"
        const val EXTRA_REPLY_GENDER = "GENDER"
        const val EXTRA_REPLY_WEIGHT = "WEIGHT"
        private const val LOG_TAG = "AddPetActivity"
    }
}