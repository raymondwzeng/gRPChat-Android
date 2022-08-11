package com.macandswiss.grpchatandroid.fragments.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.macandswiss.grpchatandroid.grpc.ChatRPC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

/**
 * @author Raymond Zeng
 *
 * A ViewModel that holds information about the current chat session as a whole. Also manages the
 * local client's chatting.
 */
class ChatViewModel : ViewModel() {
    val messages = mutableListOf<Message>()

    suspend fun connectionEstablished() {
        ChatRPC.getChannelInfo().onSuccess {
            messages.add(Message("System", it.topic))
            ChatRPC.subscribe().onSuccess { chatFlow ->
                chatFlow.collect { chat ->
                    println("Collected chat: $chat")
                    messages.add(Message(chat.author, chat.content))
                }
            }.onFailure {
                messages.add(Message("System", "Failed to subscribe to channel."))
            }
        } .onFailure {
            messages.add(Message("System", "Failed to connect to server."))
        }
    }

    suspend fun sendMessage(message: String) {
        ChatRPC.sendChat(message)
    }
}