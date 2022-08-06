package com.macandswiss.grpchatandroid.fragments.connection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.navigation.fragment.findNavController
import com.macandswiss.grpchatandroid.R
import com.macandswiss.grpchatandroid.databinding.FragmentConnectionBinding

class ConnectionFragment : Fragment() {

    private lateinit var viewModel: ConnectionViewModel
    private lateinit var binding: FragmentConnectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ConnectionViewModel::class.java] //TODO: Consider migrating this to be owned by the activity?
        binding = FragmentConnectionBinding.inflate(inflater, container, false)

        val navController = findNavController()

        binding.connectButton.setOnClickListener {
            navController.navigate(R.id.chatFragment)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.connect()
        }

        return binding.root
    }
}