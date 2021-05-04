package com.example.studiawdaniiapp.ui.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studiawdaniiapp.data.models.EducationalInstitution
import com.example.studiawdaniiapp.databinding.LayoutListitemBinding

class EduListAdapter(private var educationalInstitutionsList: MutableList<EducationalInstitution> = mutableListOf()) :
    RecyclerView.Adapter<EduListAdapter.EduViewHolder>() {

    inner class EduViewHolder(private val binding: LayoutListitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(educationalInstitution: EducationalInstitution) {
            binding.universityName.text = educationalInstitution.educational_institution_name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EduViewHolder {
        val binding =
            LayoutListitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EduViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: EduViewHolder, position: Int) {
        holder.bind(educationalInstitution = educationalInstitutionsList[position])
    }

    override fun getItemCount(): Int {
        return educationalInstitutionsList.size
    }

    fun setEducationalInstitutionsList(educationalInstitutionsList: MutableList<EducationalInstitution>) {
        this.educationalInstitutionsList.clear()
        this.educationalInstitutionsList.addAll(educationalInstitutionsList)
        notifyDataSetChanged()
    }
}



