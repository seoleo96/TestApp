package com.example.testapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testapp.R
import com.example.testapp.core.BaseFragment
import com.example.testapp.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var adapter: ContactsAdapter
    private val landscape = Configuration.ORIENTATION_LANDSCAPE
    private val portrait = Configuration.ORIENTATION_PORTRAIT
    private val columnCountPortrait = 2
    private val columnCountLandscape = 4

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        permissionSetup()
        fetchContacts()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == landscape) {
            setRecyclerOrientation(landscape)
        } else if (newConfig.orientation == portrait) {
            setRecyclerOrientation(portrait)
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.startTimer()
    }

    override fun onPause() {
        super.onPause()
        homeViewModel.cancelTimer()
    }

    private fun fetchContacts() {
        homeViewModel.fetchList()
        homeViewModel.successObserve(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }
    }

    private fun setAdapter() {
        adapter = ContactsAdapter { contactId ->
            homeViewModel.deleteContact(contactId)
        }
        binding.recycler.adapter = adapter
        setRecyclerOrientation(portrait)
    }

    private fun setRecyclerOrientation(orientation: Int) {
        val spanCount =
            if (orientation == portrait) columnCountPortrait else columnCountLandscape
        val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.recycler.layoutManager = gridLayoutManager
    }

    private fun permissionSetup() {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.READ_CONTACTS)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            permissionsResultCallback.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    private val permissionsResultCallback = registerForActivityResult(
        RequestPermission()) { hasPermission ->
        when (hasPermission) {
            true -> {
                homeViewModel.insertContacts()
            }
            false -> {
                Toast.makeText(requireContext(),
                    getString(R.string.toast_text),
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}