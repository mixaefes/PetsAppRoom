package com.example.petsapproom.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

import com.example.petsapproom.data.room.EntityPet
import com.example.petsapproom.databinding.PetItemViewBinding

class PetViewHolder(
    private val binding: PetItemViewBinding,
    private val onPetClickListener: OnPetClickListener
) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    fun bind(item: EntityPet) {
        binding.textItemName.text = item.name
        binding.textItemBreed.text = item.breed
        binding.textItemGender.text = when (item.gender) {
            1 -> "MALE"
            2 -> "FEMALE"
            else -> "UNKNOWN"
        }
        binding.textItemWeight.text = item.weight.toString()
        binding.root.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.i("ViewHolderClass","item was clicked")
        onPetClickListener.onPetClick(adapterPosition)
    }
}
