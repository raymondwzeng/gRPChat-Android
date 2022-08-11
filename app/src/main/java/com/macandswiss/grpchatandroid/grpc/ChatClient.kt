package com.macandswiss.grpchatandroid.grpc

import kotlinx.coroutines.flow.Flow
import java.io.Closeable

/**
 * @author Raymond Zeng
 *
 * Provides a generic template for chat clients to follow, be they RPC or some other protocol.
 */
interface ChatClient<ChannelType, ClientChatType, ServerChatType, ServerInformationType> : Closeable {
    suspend fun getChannelInfo(): Result<ServerInformationType>
    suspend fun sendChat(message: String)
    fun subscribe() : Result<Flow<ClientChatType>>
    fun connect(host: String, port: Int) : Result<ChannelType>
}