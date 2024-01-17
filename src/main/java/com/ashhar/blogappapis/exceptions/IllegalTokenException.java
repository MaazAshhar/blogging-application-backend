package com.ashhar.blogappapis.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IllegalTokenException extends RuntimeException {
	
	private String message;
	

}
