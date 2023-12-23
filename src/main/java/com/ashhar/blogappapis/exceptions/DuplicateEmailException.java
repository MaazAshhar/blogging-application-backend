package com.ashhar.blogappapis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateEmailException extends RuntimeException{
    String resourceName;
    String email;

    public DuplicateEmailException(String resourceName, String email) {
        super(String.format("%s with %s already exist",resourceName,email));
        this.resourceName = resourceName;
        this.email = email;
    }
}
