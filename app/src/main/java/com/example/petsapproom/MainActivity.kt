package com.example.petsapproom

import android.content.Intent
import android.content.SharedPreferences
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
import com.example.petsapproom.data.cursor.PetOpenHelper
import com.example.petsapproom.data.room.EntityPet
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
    val adapter = PetAdapter(this)

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        //preferences


        binding.floatingActionButton.setOnClickListener {
            startAddActivity()
        }
    }

    override fun onResume() {
        super.onResume()
/*        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        if(!pref.getBoolean("switch_to_cursor", false)) {
            petViewModel.allPets.observe(this) {
                it.let { adapter.submitList(it) }
            }
        }else{
            adapter.submitList(PetOpenHelper(this).getAllPets())
        // adapter.submitList(petViewModel.allPetsCursor)
        }*/
        petViewModel.getAll().observe(this) {
            it.let { adapter.submitList(it) }
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
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val isCursor = pref.getBoolean("switch_to_cursor", false)
        var pet: EntityPet? = null
        //  val petsList = if(isCursor)petViewModel.myPetDbHelper.getAllPets() else petViewModel.allPets.value
        petViewModel.getAll().observe(this) {
            pet = it[position]
            Log.i(LOG_TAG, "this is checked petsList: $it")


            //     val pet = petsList?.get(position)
            Log.i(LOG_TAG, "this is checked pet: $pet his id=${pet?.id}")
        }
        val updateIntent = Intent(this, AddPetActivity::class.java)
        // updateIntent.putExtra("id_key",petViewModel.allPets.value?.get(position)?.id)
        updateIntent.putExtra("id_key", pet?.id)
        startActivity(updateIntent)

    }

    companion object {
        private const val LOG_TAG = "MainActivity"
    }
}