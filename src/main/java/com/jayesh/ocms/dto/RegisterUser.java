package com.jayesh.ocms.dto;

import lombok.Data;

@Data
public class RegisterUser {

    private String userName;

    private String password;

    private String role;

    private String email;

}
