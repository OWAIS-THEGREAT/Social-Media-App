package com.App.socialmediaapp.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.App.socialmediaapp.remote.responsebody.Comment
import com.App.socialmediaapp.remote.responsebody.UserData
import com.App.socialmediaapp.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentBottomSheet(
    isSheetVisible: Boolean,
    sheetState: SheetState,
    comments: List<Comment>,
    userViewModel: UserViewModel,
    onClose: () -> Unit,
    onPostComment: (String) -> Unit
) {
    if (isSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { onClose() },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Comments",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    if (comments.isEmpty()) {
                        item {
                            Text(
                                text = "No Comments Yet",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    } else {
                        items(comments) { comment ->
                            val username = getUserDetail(comment.userId, userViewModel)?.username ?: "Unknown"
                            CommentItem(username, comment.content)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                var newComment by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = newComment,
                    onValueChange = { newComment = it },
                    label = { Text("Write a comment...") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (newComment.isNotBlank()) {
                            onPostComment(newComment)
                            newComment = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Post")
                }
            }
        }
    }
}

@Composable
fun getUserDetail(userId: String, userViewModel: UserViewModel): UserData? {
    val user = userViewModel.getUserById.collectAsState(initial = null)
    LaunchedEffect(userId) {
        userViewModel.getUserById(userId)
    }
    return user.value?.data
}

@Composable
fun CommentItem(username: String, comment: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = username,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = comment,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}
