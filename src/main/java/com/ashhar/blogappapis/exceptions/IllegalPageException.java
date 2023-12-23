package com.ashhar.blogappapis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IllegalPageException extends RuntimeException{
	String message;

	public IllegalPageException(String message) {
		super();
		this.message = message;
	}
	

}
