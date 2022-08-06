package com.macandswiss.backend

import io.grpc.Server
import io.grpc.ServerBuilder
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class Application {
    val server: Server = ServerBuilder.forPort(8080).addService(ChatService()).build()

    internal class ChatService : ChatServiceGrpcKt.ChatServiceCoroutineImplBase() {
        override suspend fun getServerInformation(request: Grpchat.Nothing): Grpchat.ServerInformation {
            return Grpchat.ServerInformation.newBuilder()
                .setTopic("Hello from the server! The date and time is ${LocalDateTime.now()}!")
                .build()
        }

        override fun chatBidirectional(requests: Flow<Grpchat.ChatToServer>): Flow<Grpchat.ChatToClient> {
            return super.chatBidirectional(requests)
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
