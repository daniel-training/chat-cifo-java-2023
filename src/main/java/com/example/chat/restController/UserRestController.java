package com.example.chat.restController;

import com.example.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users") //TODO get version for path mapping from configuration)
public class UserRestController {

    @Autowired
    UserService userService;

}
