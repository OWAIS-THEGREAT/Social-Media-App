package com.App.socialmediaapp.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.App.socialmediaapp.viewmodels.ChatBotViewModel

data class ChatMessage(val text: String, val isUser: Boolean)

@Composable
fun ChatScreen(viewModel: ChatBotViewModel) {
    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    var chatHistory by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }

    val botResponse by viewModel.response

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp),
            reverseLayout = true
        ) {
            items(chatHistory.reversed()) { message ->
                ChatBubble(message = message)
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .height(56.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (userInput.text.isNotBlank()) {
                        val userMessage = userInput.text
                        chatHistory = chatHistory + ChatMessage(userMessage, isUser = true)
                        userInput = TextFieldValue("")

                        viewModel.sendMessage(userMessage)
                    }
                },
                modifier = Modifier
                    .height(56.dp)
                    .padding(4.dp)
            ) {
                Text("Send")
            }
        }
    }


    LaunchedEffect(botResponse) {
        if (botResponse.isNotEmpty()) {
            chatHistory = chatHistory + ChatMessage(botResponse.last(), isUser = false)
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (message.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .widthIn(max = 250.dp)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}
