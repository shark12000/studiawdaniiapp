package com.example.studiawdaniiapp.ui.fragments.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studiawdaniiapp.databinding.AdminDataItemBinding
import com.example.studiawdaniiapp.domain.models.DataToReceive

class AdminAdapter(private var list: MutableList<DataToReceive> = mutableListOf()) :
    RecyclerView.Adapter<AdminAdapter.ProgrammeViewHolder>() {

    var clicked: OnButtonClicked? = null

    inner class ProgrammeViewHolder(private val binding: AdminDataItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataToReceive: DataToReceive) {
            binding.education.text = dataToReceive.education
            binding.userName.text = dataToReceive.userName
            binding.acceptBtn.setOnClickListener() {
                clicked?.onAcceptButtonPressed(absoluteAdapterPosition)
            }
            binding.declineBtn.setOnClickListener() {
                clicked?.onCancelButtonPressed(absoluteAdapterPosition)
            }
        }
    }

    fun getItem(position: Int): DataToReceive {
        return list[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgrammeViewHolder {
        val binding =
            AdminDataItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProgrammeViewHolder(binding = binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeItem(index: Int) {
        list.removeAt(index)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProgrammeViewHolder, position: Int) {
        holder.bind(dataToReceive = list[position])
    }

    fun setList(list: List<DataToReceive>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnButtonItemClickListener(clickListener: OnButtonClicked) {
        clicked = clickListener
    }
}


