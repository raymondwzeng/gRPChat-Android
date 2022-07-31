package com.macandswiss.grpchatandroid.fragments.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.macandswiss.grpchatandroid.R

/**
 *  @author Raymond Zeng
 *
 *  Standard RecyclerView Adapter for individual chat messages.
 */
class ChatAdapter(private val messages: List<Message>): RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {
    class MessageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val author: TextView
        val content: TextView

        init {
            author = view.findViewById(R.id.author)
            content = view.findViewById(R.id.content)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.author.text = messages[position].author
        holder.content.text = messages[position].content
    }

    override fun getItemCount(): Int = messages.size
}

data class Message(
    val author: String,
    val content: String
)