package com.App.socialmediaapp.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.App.socialmediaapp.ui.theme.MainBackground
import java.io.File

@Composable
fun AddPostPage(
    selectedImageFile: File?,
    selectedImageTimestamp: Long,
    onPickImage: () -> Unit,
    onUploadPost: (caption: String) -> Unit
) {
    var caption by remember { mutableStateOf("") }
    val updateKey = remember(selectedImageTimestamp) { selectedImageTimestamp }
    if(selectedImageFile!=null){
        Log.d("update",updateKey.toString())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add a Post",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageFile != null) {
                AsyncImage(
                    model = "${selectedImageFile.absolutePath}?timestamp=$selectedImageTimestamp",
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = "No Image Selected",
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onPickImage() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainBackground
            )
        ) {
            Text("Select Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = caption,
            onValueChange = { caption = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White, shape = MaterialTheme.shapes.small)
                .padding(8.dp),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            ),
            decorationBox = { innerTextField ->
                if (caption.isEmpty()) {
                    Text(
                        text = "Add a caption...",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onUploadPost(caption) },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedImageFile != null && caption.isNotBlank()
        ) {
            Text("Upload Post")
        }
    }
}


