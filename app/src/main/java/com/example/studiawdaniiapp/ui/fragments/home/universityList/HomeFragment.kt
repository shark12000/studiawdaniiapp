package com.example.studiawdaniiapp.ui.fragments.home.universityList

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studiawdaniiapp.R
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.databinding.FragmentHomeBinding
import com.google.android.material.appbar.AppBarLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter = EduListAdapter()
    private val eduViewModel: EduViewModel by viewModel()
    private val TAG = "HomeFragment"
    private lateinit var searchUniversities: EditText

    private val STANDARD_APPBAR: Int = 0
    private val SEARCH_APPBAR: Int = 1
    private var mAppBarState: Int = 0

    private lateinit var viewContactsBar: AppBarLayout
    private lateinit var searchBar: AppBarLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewContactsBar = binding.root.findViewById(R.id.viewContactsToolbar) as AppBarLayout
        searchBar = binding.root.findViewById(R.id.searchToolbar) as AppBarLayout
        searchUniversities = binding.root.findViewById(R.id.etSearchContacts)
        setAppBarState(STANDARD_APPBAR)
        Log.d(TAG, "onCreateView: started")
        setUpAdapter()
        initialiseRecycler()
        initialiseAdapter()
        observeData()

        val ivSearchContact: ImageView = requireView().findViewById(R.id.ivSearchIcon) as ImageView
        ivSearchContact.setOnClickListener {
            searchUniversities.text.clear()
            Log.d(TAG, "onClick: clicked searched icon")
            toggleToolBarState()
        }
        val ivBackArrow: ImageView = requireView().findViewById(R.id.ivBackArrow) as ImageView
        ivBackArrow.setOnClickListener {
            Log.d(TAG, "onClick: clicked back arrow.")
            toggleToolBarState()
        }

        searchUniversities.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                val text: String =
                    searchUniversities.text.toString().toLowerCase(Locale.getDefault())
                adapter.filtering(text)
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })

    }

    private fun initialiseRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initialiseAdapter() {
        binding.recyclerView.adapter = adapter
    }

    private fun setUpAdapter() {
        adapter.setOnButtonItemClickListener(onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val university = adapter.getItem(position)
                val promoCode = university.eduId
                val directions = HomeFragmentDirections.actionHomeFragmentToUniversityInfoFragment(
                    promoCode
                )
                findNavController().navigate(directions)
            }
        })
    }

    private fun observeData() {
        eduViewModel.fetchEducationalInstitutionsList.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    adapter.setEducationalInstitutionsList(it.data)
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        context,
                        "An error has occurred:${it.string}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun toggleToolBarState() {
        Log.d(TAG, "toggleToolBarState: toggling AppBarState.");
        if (mAppBarState == STANDARD_APPBAR) {
            setAppBarState(SEARCH_APPBAR)
        } else {
            setAppBarState(STANDARD_APPBAR)
        }
    }

    private fun setAppBarState(state: Int) {
        Log.d(TAG, "setAppBaeState: changing app bar state to: " + state);

        mAppBarState = state
        if (mAppBarState == STANDARD_APPBAR) {
            searchBar.visibility = View.GONE
            viewContactsBar.visibility = View.VISIBLE

            val view: View = requireView()
            val im: InputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            try {
                im.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (e: NullPointerException) {
                Log.d(TAG, "setAppBaeState: NullPointerException: " + e);
            }
        } else if (mAppBarState == SEARCH_APPBAR) {
            viewContactsBar.visibility = View.GONE
            searchBar.visibility = View.VISIBLE
            val im: InputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    override fun onResume() {
        super.onResume()
        setAppBarState(STANDARD_APPBAR)
    }
}

