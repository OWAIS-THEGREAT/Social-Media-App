package com.App.socialmediaapp.views.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.App.socialmediaapp.ui.theme.ButtonColor
import com.App.socialmediaapp.ui.theme.MainBackground


@Composable
fun Login(onLoginClick: (String,String) -> Unit,onClickMoveSignup : () -> Unit){

    var username by remember{
        mutableStateOf("")
    }
    var password by remember{
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground)
            .padding(16.dp)
            ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back",
            color = Color.Black,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.fillMaxWidth().height(30.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {username = it},
            label = { Text(text = "Username", color = Color.Black) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            ),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(30.dp)),
            singleLine = true
        )

        Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = { Text(text = "Password", color = Color.Black) },
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            ),
            shape = RoundedCornerShape(30.dp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(30.dp))
            , singleLine = true
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
        Text(text = "Forgot Password?", color = Color.Black,
            modifier = Modifier
                .padding(end = 25.dp)
                .align(Alignment.End)
                .clickable {

                },
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.fillMaxWidth().height(30.dp))

        Button(
            onClick = {
                onLoginClick(username,password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(30.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColor
            )
        ) {
            Text(text = "Login", color = Color.White, fontSize = 20.sp,
                modifier = Modifier.padding(5.dp))
        }

        Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
        Text(text = "Create New Account", color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(end = 25.dp)
                .clickable {
                    onClickMoveSignup()
                },
            fontWeight = FontWeight.Bold
        )
    }
}