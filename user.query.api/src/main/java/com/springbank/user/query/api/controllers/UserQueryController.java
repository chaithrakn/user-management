package com.springbank.user.query.api.controllers;

import com.springbank.user.core.dto.BaseResponse;
import com.springbank.user.query.api.dto.UserLookupResponse;
import com.springbank.user.query.api.queries.FindAllUsersQuery;
import com.springbank.user.query.api.queries.FindUserByIdQuery;
import com.springbank.user.query.api.queries.SearchUsersQuery;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userLookup")
public class UserQueryController {
    private final QueryGateway queryGateway;

    @Autowired
    public UserQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "/")
    public ResponseEntity<UserLookupResponse> getAllUsers() {
        try {
            var query = new FindAllUsersQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();
            if (response == null || response.getUsers() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new UserLookupResponse(response.getUsers()), HttpStatus.OK);
        } catch (Exception e) {
            String emsg = "Failed to get all messages";
            return new ResponseEntity<>(new UserLookupResponse(emsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byFilter/{filter}")
    public ResponseEntity<UserLookupResponse> getUserById(@PathVariable(value = "filter") String filter) {
        try {
            var query = new SearchUsersQuery(filter);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();
            if (response == null || response.getUsers() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new UserLookupResponse(response.getUsers()), HttpStatus.OK);
        } catch (Exception e) {
            String emsg = "Failed to get user by filter: " + filter;
            return new ResponseEntity<>(new UserLookupResponse(emsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<UserLookupResponse> searchUsers(@PathVariable(value = "id") String id) {
        try {
            var query = new FindUserByIdQuery(id);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();
            if (response == null || response.getUsers() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new UserLookupResponse(response.getUsers()), HttpStatus.OK);
        } catch (Exception e) {
            String emsg = "Failed to get user by id: " + id;
            return new ResponseEntity<>(new UserLookupResponse(emsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
