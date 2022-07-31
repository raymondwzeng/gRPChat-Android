package com.macandswiss.grpchatandroid.fragments.chat

import androidx.lifecycle.ViewModel

/**
 * @author Raymond Zeng
 *
 * A ViewModel that holds information about the current chat session as a whole. Also manages the
 * local client's chatting.
 */
class ChatViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val messages = mutableListOf<Message>()

    fun sendMessage(message: String) {
        //TODO: Also grab the author from somewhere
        messages.add(Message(
            author = "Unknown",
            content = message
        ))
    }

    companion object SampleChatData {
        val messages = listOf(
            Message(
                author = "MacAndSwiss",
                content = "Hello, world!"
            ),
            Message(
                author = "MacAndSwiss",
                content = "This is a test of the recyclerview!"
            )
        )
    }
}