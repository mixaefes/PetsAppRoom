package com.example.petsapproom.adapter

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.recyclerview.widget.RecyclerView
import com.example.petsapproom.PetsApplication
import com.example.petsapproom.R

import com.example.petsapproom.data.EntityPet
import com.example.petsapproom.databinding.PetItemViewBinding

class PetViewHolder(private val binding: PetItemViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: EntityPet) {
        binding.textItemName.text = item.name
        binding.textItemBreed.text = item.breed
        binding.textItemGender.text = when (item.gender) {
            1 -> "MALE"
                2 -> "FEMALE"
            else -> "UNKNOWN"
        }
        binding.textItemWeight.text = item.weight.toString()
    }
}
