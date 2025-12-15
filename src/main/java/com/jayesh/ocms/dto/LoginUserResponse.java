package com.jayesh.ocms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginUserResponse extends GenericResponse {

    private String token;
}
