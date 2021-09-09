package com.example.petsapproom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import com.example.petsapproom.databinding.ActivityMainBinding
import com.example.petsapproom.model.PetViewModelFactory
import com.example.petsapproom.model.PetsViewModel
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petsapproom.adapter.OnPetClickListener
import com.example.petsapproom.adapter.PetAdapter
import com.example.petsapproom.settings.SettingsActivity

class MainActivity : AppCompatActivity(), OnPetClickListener {
    private lateinit var binding: ActivityMainBinding
    private val activityRequestCode = 1
    private lateinit var myIntent: Intent

    private val petViewModel: PetsViewModel by viewModels {
        PetViewModelFactory(
            (application as PetsApplication).repository,
            PreferenceManager.getDefaultSharedPreferences(application.applicationContext),
            application.applicationContext
        )
    }
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PetAdapter(this)
        binding.recyclerView.adapter = adapter

        petViewModel.allPets.observe(this) {
            it.let { adapter.submitList(it) }
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
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings_id -> {
                val intentMenu = Intent(this, SettingsActivity::class.java)
                startActivity(intentMenu)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPetClick(position: Int) {
        val petsList = petViewModel.allPets.value
        val pet = petsList?.get(position)
        Log.i(LOG_TAG,"this is checked pet: $pet")
        val updateIntent = Intent(this, AddPetActivity::class.java)
        updateIntent.putExtra("id_key",petViewModel.allPets.value?.get(position)?.id)
        startActivity(updateIntent)
    }
    companion object{
       private const val LOG_TAG = "MainActivity"
    }
}