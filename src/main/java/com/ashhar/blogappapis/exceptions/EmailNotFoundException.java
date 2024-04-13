package com.ashhar.blogappapis.exceptions;

import java.text.MessageFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmailNotFoundException extends RuntimeException{
	
	private String resource;
	private String field;
	private String fieldValue;
	
	public EmailNotFoundException(String resource,String field,String fieldValue) {
		super(MessageFormat.format("{0} not found with {1}: {2}",resource,field,fieldValue));
		this.resource=resource;
		this.field=field;
		this.fieldValue=fieldValue;
	}

}
