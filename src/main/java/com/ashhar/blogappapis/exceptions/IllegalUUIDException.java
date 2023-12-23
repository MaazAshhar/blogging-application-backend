package com.ashhar.blogappapis.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class IllegalUUIDException extends RuntimeException{
    private String message="Not a valid UUID";
}
