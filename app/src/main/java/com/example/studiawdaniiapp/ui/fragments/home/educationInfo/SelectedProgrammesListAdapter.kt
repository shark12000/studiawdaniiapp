package com.example.studiawdaniiapp.ui.fragments.home.educationInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studiawdaniiapp.databinding.SelectedProgrammeItemBinding
import com.example.studiawdaniiapp.domain.models.Programme

class SelectedProgrammesListAdapter(private var programmeList: MutableList<Programme> = mutableListOf()) :
    RecyclerView.Adapter<SelectedProgrammesListAdapter.ProgrammeViewHolder>() {

    var deleteButtonClickListener: DeleteButtonClickListener? = null

    inner class ProgrammeViewHolder(private val binding: SelectedProgrammeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(programme: Programme) {
            binding.programmeName.text = programme.programmeName
            binding.city.text = programme.city
            binding.deleteButton.setOnClickListener() {
                deleteButtonClickListener?.deleteItemClick(absoluteAdapterPosition)
            }
        }
    }


    fun setOnButtonItemClickListener(deleteButtonClickListener: DeleteButtonClickListener) {
        this.deleteButtonClickListener = deleteButtonClickListener
    }

    fun getListOfProgrammes() : List<Programme> {
        return programmeList
    }

    fun getItem(position: Int): Programme {
        return programmeList[position]
    }

    fun removeItem(position: Int) {
        programmeList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgrammeViewHolder {
        val binding =
            SelectedProgrammeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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