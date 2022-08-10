package com.macandswiss.grpchatandroid.grpc

import ChatServiceGrpcKt
import Grpchat
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

object ChatRPC : ChatClient<ManagedChannel, Grpchat.ChatToClient, Grpchat.ChatToServer, Grpchat.ServerInformation> {
    private lateinit var channel: ManagedChannel
    private lateinit var stub : ChatServiceGrpcKt.ChatServiceCoroutineStub

    override suspend fun getChannelInfo() : Result<Grpchat.ServerInformation> {
        return try {
            Result.success(stub.getServerInformation(Grpchat.Nothing.newBuilder().build()))
        } catch (e: Exception) {
            Result.failure(Throwable(e.message.toString()))
        }
    }

    override fun sendChat(message:String): Flow<Grpchat.ChatToClient> {
        return stub.chatBidirectional(chatRequest(message))
    }

    private fun chatRequest(message: String) : Flow<Grpchat.ChatToServer> = flow {
        emit(Grpchat.ChatToServer.newBuilder().setContent(message).build())
    }

    override fun close() { //TODO: Do we need to think about how to manage the flows and stuff once the channel is down?
        channel.shutdownNow()
    }

    override fun connect(host: String, port: Int): Result<ManagedChannel> {
        return try {
            channel = let {
                val builder = ManagedChannelBuilder.forAddress(host, port).usePlaintext()
                builder.executor(Dispatchers.IO.asExecutor()).build()
            }
            stub = ChatServiceGrpcKt.ChatServiceCoroutineStub(channel)
            Result.success(channel)
        } catch(e: Exception) {
            Result.failure(Throwable(e.message.toString()))
        }
    }
}