package com.example.demo.socialmedia.controllers;


import com.example.demo.socialmedia.dtos.UserDTO;
import com.example.demo.socialmedia.entities.*;
import com.example.demo.socialmedia.services.UserService;
import com.example.demo.socialmedia.utils.ApiResponse;
import com.example.demo.socialmedia.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<UserDTO>> loginUser(@RequestBody User user){

        String username = user.getUsername();
        String password = user.getPassword();
        Optional<User> tempUser = userService.loginUser(username,password);
        return tempUser.map(value -> ResponseEntity.ok(new ApiResponse<>(UserMapper.toUserDTO(value), "User Found"))).orElseGet(() -> ResponseEntity.status(404).body(new ApiResponse<>(null, "User Not Found")));

    }

    @GetMapping("user/{userid}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable String userid) {

        if(userService.findUserById(userid).isPresent()){
            User user = userService.findUserById(userid).get();

            return ResponseEntity.ok(new ApiResponse<>(UserMapper.toUserDTO(user),"User Found"));
        }
        else{
            return ResponseEntity.status(404).body(new ApiResponse<>(null,"User Not Found"));
        }
    }


    @PostMapping("user")
    public ResponseEntity<ApiResponse<UserDTO>> saveUser(@RequestBody User user){
        user.setId(UUID.randomUUID().toString());
        User tempuser =  userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(UserMapper.toUserDTO(tempuser),"User Created Succesfully"));
    }

    @GetMapping("users")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers(){
        List<UserDTO> userDTOList = userService.findAllUsers().stream().map(UserMapper::toUserDTO).toList();
        return ResponseEntity.ok(new ApiResponse<>(userDTOList,"Success Response"));
    }

    @DeleteMapping("user/{userid}")
    public void deleteUser(@PathVariable String userid){
        User user = userService.findUserById(userid).get();
        userService.deleteUser(user);
    }
}
