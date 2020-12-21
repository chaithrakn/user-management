package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.commands.UpdateUserCommand;
import com.springbank.user.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/updateUser")
public class UpdateUserController {
    private final CommandGateway commandGateway;

    @Autowired // gets injected via dependency injection
    public UpdateUserController(CommandGateway cg) {
        this.commandGateway = cg;
    }

    @PutMapping(path = "/{id}") // full update.
    public ResponseEntity<BaseResponse> updateUser(@PathVariable(value = "id") String id,
                                                   @Valid @RequestBody UpdateUserCommand command) {

        try {
            command.setId(id);
            commandGateway.send(command);
            return new ResponseEntity<>(new BaseResponse("User successfully updated."), HttpStatus.OK);
        } catch (Exception e){
            var emsg = "Error while processing update user id: " + command.getId();
            //print to container log
            System.out.println(emsg);
            return new ResponseEntity<>(new BaseResponse(emsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
