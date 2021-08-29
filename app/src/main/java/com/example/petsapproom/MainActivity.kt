package com.example.petsapproom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.petsapproom.data.PetsRepository
import com.example.petsapproom.databinding.ActivityMainBinding
import com.example.petsapproom.model.PetViewModelFactory
import com.example.petsapproom.model.PetsViewModel
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petsapproom.adapter.PetAdapter
import com.example.petsapproom.data.EntityPet

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val activityRequestCode = 1

    private val petViewModel : PetsViewModel by viewModels{
            PetViewModelFactory((application as PetsApplication).repository)
        }
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PetAdapter()
        binding.recyclerView.adapter = adapter

        petViewModel.allPets.observe( this){
            it.let{adapter.submitList(it)}
        }


        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val name = it.data?.getStringExtra(AddPetActivity.EXTRA_REPLY_NAME)
                    val breed = it.data?.getStringExtra(AddPetActivity.EXTRA_REPLY_BREED)
                    val gender = it.data?.getIntExtra(AddPetActivity.EXTRA_REPLY_GENDER, 0)
                    val weight = it.data?.getIntExtra(AddPetActivity.EXTRA_REPLY_WEIGHT, 0)
                    Toast.makeText(
                        this,
                        "thanks for adding information. name = $name breed = $breed gender = $gender weight = $weight",
                        Toast.LENGTH_SHORT
                    ).show()
                    val pet = EntityPet(name=name!!,breed = breed!!,gender = gender!!, weight = weight!!)
                    petViewModel.insertPet(pet)

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