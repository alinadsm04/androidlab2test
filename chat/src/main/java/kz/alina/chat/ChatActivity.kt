package kz.alina.chat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class ChatActivity : AppCompatActivity() {

    private lateinit var adapter: ChatAdapter
    private lateinit var webSocket: WebSocket
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val messageEditText = findViewById<EditText>(R.id.messageEditText)
        val sendButton = findViewById<Button>(R.id.sendButton)

        adapter = ChatAdapter(mutableListOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize WebSocket connection.
        initWebSocket()

        sendButton.setOnClickListener {
            val message = messageEditText.text.toString().trim()
            if (message.isNotEmpty()) {
                // Send the message using WebSocket.
                webSocket.send(message)
                // Immediately add the sent message to the chat.
                adapter.addMessage(ChatMessage(message, isSent = true))
                messageEditText.text.clear()
            }
        }
    }

    private fun initWebSocket() {
        val request = Request.Builder().url("wss://echo.websocket.org").build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                runOnUiThread {
                    Toast.makeText(this@ChatActivity, "WebSocket Connected", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                runOnUiThread {
                    // Add the echoed message to the chat as a received message.
                    adapter.addMessage(ChatMessage(text, isSent = false))
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                // In this example, we focus on text messages.
                onMessage(webSocket, bytes.utf8())
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                runOnUiThread {
                    Toast.makeText(this@ChatActivity, "WebSocket Closing: $reason", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                runOnUiThread {
                    Toast.makeText(this@ChatActivity, "WebSocket Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocket.close(1000, "Activity destroyed")
        client.dispatcher.executorService.shutdown()
    }
}
