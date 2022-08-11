package com.macandswiss.backend

import io.grpc.Server
import io.grpc.ServerBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime

class Application {
    val server: Server = ServerBuilder.forPort(8080).addService(ChatService()).build()
    internal class ChatService : ChatServiceGrpcKt.ChatServiceCoroutineImplBase() {
        private val chatFlow = MutableSharedFlow<Grpchat.ChatToClient>()
        override suspend fun getServerInformation(request: Grpchat.Nothing): Grpchat.ServerInformation {
            return Grpchat.ServerInformation.newBuilder()
                .setTopic("Hello from the server! The date and time is ${LocalDateTime.now()}!")
                .build()
        }

        override suspend fun sendChat(request: Grpchat.ChatToServer): Grpchat.Nothing {
            chatFlow.emit(Grpchat.ChatToClient.newBuilder().setAuthor("Unknown").setContent(request.content).build())
            return Grpchat.Nothing.newBuilder().build()
        }

        override fun subscribeToChannel(request: Grpchat.Nothing): Flow<Grpchat.ChatToClient> {
            return chatFlow
        }
    }

    fun start() {
        server.start()
        println("Server started.")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("Shutting down server...")
                this@Application.stop()
                println("Server shut down.")
            }
        )
    }

    fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

}

fun main() {
    val server = Application()
    server.start()
    server.blockUntilShutdown()
}
