package com.springbank.user.query.api.dto;

import com.springbank.user.core.dto.BaseResponse;
import com.springbank.user.core.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserLookupResponse extends BaseResponse {
    private List<User> users;

    public UserLookupResponse(String msg) {
        super(msg);
    }

    public UserLookupResponse(List<User> users) {
        super(null);
        this.users = users;
    }

    public UserLookupResponse(String msg, User user) {
        super(msg);
        this.users = new ArrayList<User>();
        this.users.add(user);
    }

    public UserLookupResponse(User user) {
        super(null);
        this.users = new ArrayList<User>();
        this.users.add(user);
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
