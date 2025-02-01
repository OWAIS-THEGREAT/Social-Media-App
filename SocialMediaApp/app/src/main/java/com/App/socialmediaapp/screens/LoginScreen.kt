package com.App.socialmediaapp.screens

import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.App.socialmediaapp.remote.requestbody.UserRequestBody
import com.App.socialmediaapp.utils.UserPreferences
import com.App.socialmediaapp.viewmodels.UserViewModel
import com.App.socialmediaapp.views.authentication.Login
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(userViewModel: UserViewModel, navController: NavHostController){

    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val coroutineScope = rememberCoroutineScope()

    val isLoading = userViewModel.isloading.collectAsState()
    val error = userViewModel.error.collectAsState()
    val loginUser = userViewModel.loginUser.collectAsState()

    val showDialog = remember { mutableStateOf(false) }

    Login(
        onLoginClick = { username, password ->
            userViewModel.loginUser(UserRequestBody(username = username, password = password))
        },
        onClickMoveSignup = {
            navController.navigate("Signup")
        }
    )

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
    }
    else if(error.value.isNotEmpty()){
        showDialog.value = true
    }
    else{
        if (loginUser.value.data != null) {

            LaunchedEffect(loginUser.value.data) {

                coroutineScope.launch {
                    userPreferences.saveUserId(loginUser.value.data!!.id)
                }

                userViewModel.clearLoginUser()
                navController.navigate("SocialMediaMainScreen") {
                    popUpTo("Login") { inclusive = true }
                }
            }
        }
    }
}