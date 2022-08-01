package com.macandswiss.backend

import io.grpc.Server
import io.grpc.ServerBuilder

class Application {
    val server: Server = ServerBuilder.forPort(8080).addService(ChatService()).build()

    internal class ChatService : ChatServiceGrpcKt.ChatServiceCoroutineImplBase() {
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
