package com.macandswiss.grpchatandroid.fragments.connection

import androidx.lifecycle.ViewModel
import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.Closeable

/**
 * @author Raymond Zeng
 *
 * A ViewModel that holds the information about the connection, such as the current Host IP and Nickname.
 */
class ConnectionViewModel : ViewModel() {
    private val channelBuilder = ManagedChannelBuilder.forAddress("localhost", 8080)
    private lateinit var channel: Channel

    class ChatRCP() : Closeable {
        private val channel = let {
            println("Connecting to the server...")

            val builder = ManagedChannelBuilder.forAddress("10.0.2.2", 8080).usePlaintext()

            builder.executor(Dispatchers.IO.asExecutor()).build()
        }

        val stub = ChatServiceGrpcKt.ChatServiceCoroutineStub(channel)

        suspend fun getChannelInfo() {
            try {
                val response = stub.getServerInformation(Grpchat.Nothing.newBuilder().build())
                println(response)
            } catch (e: Exception) {
                println("Error while processing request: ${e.message}")
                e.printStackTrace()
            }
        }

        override fun close() {
            channel.shutdownNow()
        }
    }

    suspend fun connect() {
        val chatRCP = ChatRCP()
        coroutineScope {
            launch {
                chatRCP.getChannelInfo()
                chatRCP.close()
            }
        }
    }
}
