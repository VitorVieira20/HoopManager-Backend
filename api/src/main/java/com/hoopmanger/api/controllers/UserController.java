package com.hoopmanger.api.controllers;

import com.hoopmanger.api.domain.user.UserResponseDTO;
import com.hoopmanger.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api/user" )
public class UserController {

    //@Autowired
    //private UserService userService;

    @GetMapping
    public ResponseEntity/*<UserResponseDTO>*/ getEvents(){
        //UserResponseDTO user = this.userService.getUser();
        return ResponseEntity.ok("Sucesso");
    }
}
