package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.commands.RegisterUserCommand;
import com.springbank.user.cmd.api.dto.RegisterUserResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/registerUser")
public class RegisterUserController {
    private final CommandGateway commandGateway;

    @Autowired
    public RegisterUserController(CommandGateway cg) {
        this.commandGateway = cg;
    }

    @PostMapping //POST api
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandGateway.send(command);
            return new ResponseEntity<>(new RegisterUserResponse(id,"User successfully registered. id: " +
                    command.getId()), HttpStatus.CREATED);
        } catch (Exception e){
            var emsg = "Error while processing register user id: " + command.getId();
            //print to container log
            System.out.println(emsg);
            return new ResponseEntity<>(new RegisterUserResponse(id, emsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
