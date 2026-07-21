package com.example.mobilesurapp.api

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketClient @Inject constructor(
    private val gson: Gson
) {

    companion object {
        private const val TAG = "WebSocketClient"
    }

    private val client = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()

    private var socket: WebSocket? = null

    private var currentUrl: String? = null

    private val _connected = MutableStateFlow(false)
    val connected: StateFlow<Boolean> = _connected

    private val incomingChannel = Channel<String>(Channel.BUFFERED)

    val incomingMessages = incomingChannel.receiveAsFlow()

    fun connect(url: String) {

        if (_connected.value) return

        currentUrl = url

        val request = Request.Builder()
            .url(url)
            .build()

        socket = client.newWebSocket(
            request,
            object : WebSocketListener() {

                override fun onOpen(
                    webSocket: WebSocket,
                    response: Response
                ) {

                    Log.d(TAG, "Connected")

                    _connected.value = true

                }

                override fun onMessage(
                    webSocket: WebSocket,
                    text: String
                ) {

                    Log.d(TAG, "Received: $text")

                    incomingChannel.trySend(text)

                }

                override fun onMessage(
                    webSocket: WebSocket,
                    bytes: ByteString
                ) {

                }

                override fun onClosing(
                    webSocket: WebSocket,
                    code: Int,
                    reason: String
                ) {

                    webSocket.close(1000, null)

                }

                override fun onClosed(
                    webSocket: WebSocket,
                    code: Int,
                    reason: String
                ) {

                    Log.d(TAG, "Closed")

                    _connected.value = false

                    socket = null

                }

                override fun onFailure(
                    webSocket: WebSocket,
                    t: Throwable,
                    response: Response?
                ) {

                    Log.e(TAG, "Failure", t)

                    _connected.value = false

                    socket = null

                }

            }
        )

    }

    fun disconnect() {

        socket?.close(1000, "Disconnected")

        socket = null

        _connected.value = false

    }

    fun send(request: Any): Boolean {

        val json = gson.toJson(request)

        Log.d(TAG, "Sending: $json")

        return socket?.send(json) ?: false

    }

}