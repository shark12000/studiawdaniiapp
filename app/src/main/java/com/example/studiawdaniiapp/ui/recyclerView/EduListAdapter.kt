package com.example.studiawdaniiapp.ui.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studiawdaniiapp.data.models.EducationalInstitution
import com.example.studiawdaniiapp.databinding.LayoutListitemBinding

class EduListAdapter(var eduList: List<EducationalInstitution>) :
    RecyclerView.Adapter<EduListAdapter.EduViewHolder>() {

    inner class EduViewHolder(private val binding: LayoutListitemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(educationalInstitution: EducationalInstitution) {
            binding.universityName.setText(educationalInstitution.educational_institution_name)
            //binding.image.setImageBitmap(decode(educationalInstitution.imageUrl))
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EduViewHolder {

        val binding = LayoutListitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EduViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EduViewHolder, position: Int) {
        holder.bind(eduList.get(position))
    }

    override fun getItemCount(): Int {
        return eduList.size
    }
}



