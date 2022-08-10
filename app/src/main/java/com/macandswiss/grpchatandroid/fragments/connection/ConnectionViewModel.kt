package com.macandswiss.grpchatandroid.fragments.connection

import ChatToServerKt
import androidx.lifecycle.ViewModel
import com.macandswiss.grpchatandroid.grpc.ChatRPC
import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.io.Closeable

/**
 * @author Raymond Zeng
 *
 * A ViewModel that holds the information about the connection, such as the current Host IP and Nickname.
 */
class ConnectionViewModel : ViewModel() {
    fun connect(host: String, port: Int) {
        ChatRPC.connect(host, port)
    }
}
