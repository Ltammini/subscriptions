package com.filmland.subscription.controller;

import com.filmland.subscription.dto.UserDto;
import com.filmland.subscription.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private  AuthenticationService authenticationService ;

    /**
     *
     * @param userDto
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResponseEntity<?> login(@RequestBody UserDto userDto) throws Exception {
        return ResponseEntity.ok(authenticationService.getToken(userDto.getUserId(), userDto.getPassword()));
    }

}
