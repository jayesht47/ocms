package com.jayesh.ocms.dto;


import lombok.Data;

@Data
public class GenericResponse {

    private Boolean error;
    private String errorMessage = "";
}
