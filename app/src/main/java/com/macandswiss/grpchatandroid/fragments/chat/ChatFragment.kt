package com.macandswiss.grpchatandroid.fragments.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.macandswiss.grpchatandroid.R
import com.macandswiss.grpchatandroid.databinding.FragmentChatBinding
import com.macandswiss.grpchatandroid.databinding.FragmentConnectionBinding

class ChatFragment : Fragment() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        binding = FragmentChatBinding.inflate(inflater, container, false)

        binding.messageContainer.adapter = ChatAdapter(viewModel.messages)
        binding.messageContainer.layoutManager = LinearLayoutManager(this.context)

        binding.sendButton.setOnClickListener {
            if(binding.chatInput.text.isNotEmpty()) {
               viewModel.sendMessage(binding.chatInput.text.toString())
                binding.chatInput.text.clear()
                binding.messageContainer.adapter?.notifyDataSetChanged()
            }
        }

        return binding.root
    }
}