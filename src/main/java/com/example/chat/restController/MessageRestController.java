package com.example.chat.restController;

import com.example.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/messages") //TODO get version for path mapping from configuration)
public class MessageRestController {

    @Autowired
    MessageService messageService;

}
