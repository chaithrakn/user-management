package com.springbank.user.core.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User {

    private String id;
    @NotEmpty(message = "firstname cannot be empty")
    private String firstname;
    @NotEmpty(message = "lastname cannot be empty")
    private String lastname;
    @Email(message = "provide valid email-id")
    private String emailaddress;
    private Account account;
}
