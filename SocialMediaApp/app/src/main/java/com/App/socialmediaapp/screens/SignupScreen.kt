package com.App.socialmediaapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.App.socialmediaapp.remote.requestbody.UserRequestBody
import com.App.socialmediaapp.viewmodels.UserViewModel
import com.App.socialmediaapp.views.authentication.Signup

@Composable
fun SignupScreen(userViewModel: UserViewModel, navController: NavHostController) {

    val isLoading = userViewModel.isloading.collectAsState()
    val error = userViewModel.error.collectAsState()
    val saveUser = userViewModel.saveUser.collectAsState()

    val showDialog = remember { mutableStateOf(false) }

    Signup {username, password, email ->
        userViewModel.saveUser(UserRequestBody(username = username, password = password))
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false
                userViewModel.clearError()
            },
            confirmButton = {
                Button(onClick = { showDialog.value = false
                    userViewModel.clearError()
                }) {
                    Text("OK")
                }
            },
            title = { Text("Error") },
            text = { Text(error.value) }
        )
    }

    if(isLoading.value){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

    }else if(error.value.isNotEmpty()){
        showDialog.value = true
    }else{
        if (saveUser.value.data != null) {
            LaunchedEffect(saveUser.value.data) {
                navController.navigate("Login") {
                    popUpTo("Signup") { inclusive = true }
                }
            }
        }
    }
}