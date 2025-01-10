package com.auth.model.request;

import com.auth.enums.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UsersRequest {

    @NotNull(message = "name can not be null")
    private String name;

    @NotNull(message = "Email should be valid or not be null")
    private String email;

    @NotNull(message = "password can not be null")
    @NotBlank(message = "password can not be blank")
    @NotEmpty(message = "password can not be empty")
    private String password;

    @NotNull(message = "roles can not be null")
    private Roles roles=Roles.PRODUCER;

    public @NotNull(message = "name can not be null") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "name can not be null") String name) {
        this.name = name;
    }

    public @NotNull(message = "Email should be valid or not be null") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Email should be valid or not be null") String email) {
        this.email = email;
    }

    public @NotNull(message = "password can not be null") @NotBlank(message = "password can not be blank") @NotEmpty(message = "password can not be empty") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "password can not be null") @NotBlank(message = "password can not be blank") @NotEmpty(message = "password can not be empty") String password) {
        this.password = password;
    }

    public @NotNull(message = "roles can not be null") Roles getRoles() {
        return roles;
    }

    public void setRoles(@NotNull(message = "roles can not be null") Roles roles) {
        this.roles = roles;
    }
}