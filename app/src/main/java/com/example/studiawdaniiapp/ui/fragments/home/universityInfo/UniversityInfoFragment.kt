package com.example.studiawdaniiapp.ui.fragments.home.universityInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studiawdaniiapp.databinding.FragmentUniversityInfoBinding
import com.example.studiawdaniiapp.domain.models.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class UniversityInfoFragment : Fragment(){

    private lateinit var binding: FragmentUniversityInfoBinding
    private val adapter = ProgrammeListAdapter()
    private val viewModel: UniversityInfoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUniversityInfoBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialiseRecycler()
        initialiseAdapter()
        selectProgramme()
        observeData(getEducationalInstitutionId()!!)
    }


    private fun selectProgramme() {
        adapter.setOnButtonItemClickListener(onButtonClickListener = object :
            OnButtonClickListener {
            override fun onItemClick(position: Int, button: Button) {
                val programme = adapter.getItem(position)
                val programmeID = programme.programmeID
                viewModel.selectProgramme(programmeID)
                viewModel.booleanLiveData.observe(viewLifecycleOwner, { boolean ->
                    boolean?.let {
                        Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                        button.isEnabled = it
                    }
                })
            }
        })
    }

    private fun initialiseRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initialiseAdapter() {
        binding.recyclerView.adapter = adapter
    }

    private fun observeData(id: String) {
        viewModel.getUserProfileData(id)
        viewModel.programmeLiveData.observe(viewLifecycleOwner, {
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

    private fun getEducationalInstitutionId(): String? {
        val bundle = arguments
        if (bundle == null) {
            Toast.makeText(
                context,
                "Bundle has not arrived",
                Toast.LENGTH_SHORT
            ).show()
        }
        return UniversityInfoFragmentArgs.fromBundle(bundle!!).promoCode
    }
}
