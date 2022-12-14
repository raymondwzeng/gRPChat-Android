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
import com.macandswiss.grpchatandroid.grpc.ChatRPC

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
            val hostAndPort = binding.hostPortInput.text.toString().split(":")
            //TODO: Error handling of invalid host-port combinations
            viewModel.connect(hostAndPort[0], hostAndPort[1].toInt())
            ChatRPC.nickname = binding.nicknameInput.text.toString()
            navController.navigate(R.id.chatFragment)
        }

        return binding.root
    }
}