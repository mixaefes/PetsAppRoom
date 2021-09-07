package com.example.petsapproom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.petsapproom.data.room.EntityPet
import com.example.petsapproom.databinding.PetItemViewBinding

class PetAdapter : ListAdapter<EntityPet,PetViewHolder>(itemComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PetItemViewBinding.inflate(inflater,parent,false)
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
  private  companion object {
        val itemComparator = object :DiffUtil.ItemCallback<EntityPet>(){
            override fun areItemsTheSame(oldItem: EntityPet, newItem: EntityPet): Boolean  = oldItem==newItem

            override fun areContentsTheSame(oldItem: EntityPet, newItem: EntityPet): Boolean = oldItem.id==newItem.id

        }
    }
}