package com.example.studiawdaniiapp.ui.fragments.home.universityInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studiawdaniiapp.databinding.ProgrammeItemBinding
import com.example.studiawdaniiapp.domain.models.Programme

class ProgrammeListAdapter(private var programmeList: MutableList<Programme> = mutableListOf()) :
    RecyclerView.Adapter<ProgrammeListAdapter.ProgrammeViewHolder>() {

    var buttonClickListener: OnButtonClickListener? = null

    inner class ProgrammeViewHolder(private val binding: ProgrammeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(programme: Programme) {
            binding.programmeName.text = programme.programmeName
            binding.city.text = programme.city
            binding.description.text = programme.description
            binding.programmeType.text = programme.programmeType
            binding.programmeLength.text = programme.programmeLength
            binding.button2.setOnClickListener() {
                buttonClickListener?.onItemClick(absoluteAdapterPosition, binding.button2)
            }
        }

    }


    fun setOnButtonItemClickListener(onButtonClickListener: OnButtonClickListener) {
        this.buttonClickListener = onButtonClickListener
    }

    fun getItem(position: Int): Programme {
        return programmeList[position]
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

    fun setProgrammesList(programmeList: List<Programme>) {
        this.programmeList.clear()
        this.programmeList.addAll(programmeList)
        notifyDataSetChanged()
    }
}