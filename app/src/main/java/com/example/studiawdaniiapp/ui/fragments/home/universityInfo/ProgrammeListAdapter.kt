package com.example.studiawdaniiapp.ui.fragments.home.universityInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.databinding.ProgrammeItemBinding

class ProgrammeListAdapter(private var programmeList: MutableList<Programme> = mutableListOf()) :
    RecyclerView.Adapter<ProgrammeListAdapter.ProgrammeViewHolder>() {



    inner class ProgrammeViewHolder(private val binding: ProgrammeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(programme: Programme) {
            binding.programmeName.text = programme.programmeName
            binding.city.text = programme.city
//            binding.description.text = programme.description
//            binding.programmeType.text = programme.educationType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgrammeViewHolder {
        val binding =
            ProgrammeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProgrammeViewHolder(binding = binding)
    }

    override fun getItemCount(): Int {
        return programmeList.size
    }

    override fun onBindViewHolder(holder: ProgrammeViewHolder, position: Int) {
        holder.bind(programme = programmeList[position])
    }

    fun setProgrammesList(programmeList: MutableList<Programme>) {
        this.programmeList.clear()
        this.programmeList.addAll(programmeList)
        notifyDataSetChanged()
    }
}