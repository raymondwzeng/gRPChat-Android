package com.macandswiss.grpchatandroid.fragments.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.macandswiss.grpchatandroid.R
import com.macandswiss.grpchatandroid.databinding.FragmentChatBinding
import com.macandswiss.grpchatandroid.databinding.FragmentConnectionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatFragment : Fragment() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java] //TODO: Delegate to Activity
        binding = FragmentChatBinding.inflate(inflater, container, false)

        binding.messageContainer.adapter = ChatAdapter(viewModel.messages)
        binding.messageContainer.layoutManager = LinearLayoutManager(this.context)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.connectionEstablished()
            binding.messageContainer.adapter?.notifyDataSetChanged() //TODO: Maybe make not changed
        }

        binding.sendButton.setOnClickListener {
            if(binding.chatInput.text.isNotEmpty()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.sendMessage(binding.chatInput.text.toString())
                    binding.messageContainer.adapter?.notifyItemInserted(viewModel.messages.lastIndex)
                }
                binding.chatInput.text.clear()
            }
        }

        return binding.root
    }
}