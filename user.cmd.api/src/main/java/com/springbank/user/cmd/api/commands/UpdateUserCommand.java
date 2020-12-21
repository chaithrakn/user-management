package com.springbank.user.cmd.api.commands;

import com.springbank.user.core.models.User;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class UpdateUserCommand {
    @TargetAggregateIdentifier
    private String id;

    @Valid
    @NotNull (message = "no user details provided")
    private User user;
}
