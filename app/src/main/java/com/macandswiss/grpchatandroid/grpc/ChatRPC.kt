package com.macandswiss.grpchatandroid.grpc

import ChatServiceGrpcKt
import Grpchat
import android.app.Application
import android.net.InetAddresses
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.Flow
import java.net.InetAddress

object ChatRPC : ChatClient<ManagedChannel, Grpchat.ChatToClient, Grpchat.ChatToServer, Grpchat.ServerInformation> {
    var nickname: String = "User"
    private lateinit var channel: ManagedChannel
    private lateinit var stub : ChatServiceGrpcKt.ChatServiceCoroutineStub

    override suspend fun getChannelInfo() : Result<Grpchat.ServerInformation> {
        return try {
            Result.success(stub.getServerInformation(Grpchat.Nothing.newBuilder().build()))
        } catch (e: Exception) {
            Result.failure(Throwable(e.message.toString()))
        }
    }

    override suspend fun sendChat(message:String) {
        stub.sendChat(Grpchat.ChatToServer.newBuilder().setAuthor(nickname).setContent(message).build())
    }

    override fun subscribe(): Result<Flow<Grpchat.ChatToClient>> {
        return try {
            Result.success(stub.subscribeToChannel(Grpchat.Nothing.newBuilder().build()))
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
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