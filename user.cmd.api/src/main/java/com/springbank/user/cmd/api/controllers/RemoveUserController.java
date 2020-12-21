package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.commands.RemoveUserCommand;
import com.springbank.user.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/removeUser")
public class RemoveUserController {
    private final CommandGateway commandGateway;

    @Autowired // gets injected via dependency injection
    public RemoveUserController(CommandGateway cg) {
        this.commandGateway = cg;
    }

    @DeleteMapping(path = "/{id}") // full update.
    public ResponseEntity<BaseResponse> removeUser(@PathVariable(value = "id") String id) {

        try {

            commandGateway.send(new RemoveUserCommand(id));
            return new ResponseEntity<>(new BaseResponse("User successfully removed."), HttpStatus.OK);
        } catch (Exception e){
            var emsg = "Error while processing remove user id: " + id;
            //print to container log
            System.out.println(emsg);
            return new ResponseEntity<>(new BaseResponse(emsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
