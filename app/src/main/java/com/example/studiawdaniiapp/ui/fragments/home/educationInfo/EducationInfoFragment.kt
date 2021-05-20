package com.example.studiawdaniiapp.ui.fragments.home.educationInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studiawdaniiapp.R
import com.example.studiawdaniiapp.databinding.FragmentEducationInfoBinding
import com.example.studiawdaniiapp.domain.models.DataToSend
import com.example.studiawdaniiapp.domain.models.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class EducationInfoFragment : Fragment() {

    private lateinit var binding: FragmentEducationInfoBinding
    private val adapter = SelectedProgrammesListAdapter()
    private val viewModel: EducationInfoViewModel by viewModel()

    companion object{
        private const val SENT = "Sent"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEducationInfoBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseAdapter()
        initialiseRecycler()
        observeData()
        removeProgramme()
        onSendButtonClicked()
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.education_array,
            android.R.layout.simple_spinner_item
        ).also {
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
    }

    private fun onSendButtonClicked() {
        binding.sendBtn.setOnClickListener() {
            sendData()
        }
    }

    private fun sendData() {
        val text: String = binding.spinner.selectedItem.toString()
        val list = adapter.getListOfProgrammes()
        viewModel.sendData(
            DataToSend(
                education = text,
                programmes = list
            )
        )
        viewModel.booleanLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    viewModel.setStatus(SENT)
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        context,
                        it.string,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun observeData() {
        viewModel.getProgrammeListData()
        viewModel.educationLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    adapter.setProgrammesList(it.data)
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        context,
                        it.string,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun removeProgramme() {
        adapter.setOnButtonItemClickListener(deleteButtonClickListener = object :
            DeleteButtonClickListener {
            override fun deleteItemClick(position: Int) {
                val programme = adapter.getItem(position = position)
                adapter.removeItem(position = position)
                val programmeID = programme.programmeID
                viewModel.removeItem(programmeID)
            }
        })
    }

    private fun initialiseRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initialiseAdapter() {
        binding.recyclerView.adapter = adapter
    }

}

