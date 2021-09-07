package com.example.petsapproom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.petsapproom.data.room.PetsRepository
import com.example.petsapproom.databinding.ActivityMainBinding
import com.example.petsapproom.model.PetViewModelFactory
import com.example.petsapproom.model.PetsViewModel
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petsapproom.adapter.PetAdapter
import com.example.petsapproom.data.room.EntityPet
import com.example.petsapproom.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val activityRequestCode = 1
    private lateinit var myIntent: Intent

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

        binding.floatingActionButton.setOnClickListener {
            startAddActivity()
        }
    }


    private fun startAddActivity() {
        myIntent = Intent(this, AddPetActivity::class.java)
        startActivity(myIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.settings_id ->{
                val intentMenu = Intent(this,SettingsActivity::class.java)
                startActivity(intentMenu)
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }
}