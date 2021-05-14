package com.example.studiawdaniiapp.ui.fragments.home.universityList

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studiawdaniiapp.domain.models.EducationalInstitution
import com.example.studiawdaniiapp.databinding.LayoutListitemBinding
import java.util.*
import kotlin.collections.ArrayList

class EduListAdapter(
    private var educationalInstitutionsList: MutableList<EducationalInstitution> = mutableListOf(),
) :
    RecyclerView.Adapter<EduListAdapter.EduViewHolder>() {
    private lateinit var binding: LayoutListitemBinding
    var itemClickListener: OnItemClickListener? = null
    private var educationalInstitutionsFilterList = ArrayList<EducationalInstitution>()
    private val TAG = "EduListAdapter"

    inner class EduViewHolder(
        private val binding: LayoutListitemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(educationalInstitution: EducationalInstitution) {
            binding.universityName.text = educationalInstitution.educationalInstitutionName

            binding.seeButton.setOnClickListener {
                itemClickListener?.onItemClick(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EduViewHolder {
        binding =
            LayoutListitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EduViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: EduViewHolder, position: Int) {
        holder.bind(educationalInstitution = educationalInstitutionsList[position])
    }

    override fun getItemCount(): Int {
        return educationalInstitutionsList.size
    }

    fun setOnButtonItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun getItem(position: Int): EducationalInstitution {
        return educationalInstitutionsList[position]
    }

    fun setEducationalInstitutionsList(educationalInstitutionsList: MutableList<EducationalInstitution>) {
        this.educationalInstitutionsList.clear()
        this.educationalInstitutionsList.addAll(educationalInstitutionsList)
        educationalInstitutionsFilterList.addAll(educationalInstitutionsList)
        notifyDataSetChanged()
    }

    fun filtering(characterText: String) {
        val characterTextLine = characterText.toLowerCase(Locale.getDefault())
        educationalInstitutionsList.clear()
        if (characterTextLine.isEmpty()) {
            educationalInstitutionsList.addAll(educationalInstitutionsFilterList)
        } else {
            educationalInstitutionsList.clear()
            for (university in educationalInstitutionsFilterList) {
                if (university.educationalInstitutionName.toLowerCase(Locale.ROOT)
                        .contains(characterTextLine)
                ) {
                    Log.d(TAG, university.educationalInstitutionName)
                    educationalInstitutionsList.add(university)
                }
            }
        }
        notifyDataSetChanged()
    }
}




