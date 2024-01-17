package com.ashhar.blogappapis.exceptions;

import com.ashhar.blogappapis.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message=ex.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse> duplicateEmailExceptionHandler(DuplicateEmailException ex){
        String message=ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(IllegalUUIDException.class)
    public ResponseEntity<ApiResponse> illegalUUIDException(IllegalUUIDException ex){
        String message=ex.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        Map<String,String> resp=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error)->{
            String fieldName=error.getField();
            String errorMessage=error.getDefaultMessage();
            resp.put(fieldName,errorMessage);
        });
        return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex){
        String message="Not a valid UUID";
        return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse> postNotFoundExceptionHandler(PostNotFoundException e){
        String message=e.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalPageException.class)
    public ResponseEntity<ApiResponse> illegalPageExceptionHandler(IllegalPageException e){
    	String message= e.getMessage();
    	ApiResponse apiResponse=new ApiResponse(message,false);
    	return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<ApiResponse> invalidFileFormatExceptionHandler(InvalidFileFormatException e){
    	String message=e.getMessage();
    	ApiResponse apiResponse=new ApiResponse(message,false);
    	return new ResponseEntity<>(apiResponse,HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiResponse> fileNotFoundExceptionHandler(FileNotFoundException e){
    	String message="File doesn't exist";
    	ApiResponse apiResponse=new ApiResponse(message,false);
    	return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ApiResponse> emailNotFoundExceptionHandler(EmailNotFoundException e){
    	String message=e.getMessage();
    	ApiResponse apiResponse= new ApiResponse(message,false);
    	return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(IllegalTokenException.class)
    public ResponseEntity<ApiResponse> illegalTokenExceptionHandler(IllegalTokenException e){
    	ApiResponse apiResponse=new ApiResponse(e.getMessage(),false);
    	return new ResponseEntity<>(apiResponse,HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> apiExceptionHandler(ApiException e){
    	ApiResponse apiResponse=new ApiResponse(e.getMessage(),false);
    	return new ResponseEntity<>(apiResponse,HttpStatus.UNAUTHORIZED);
    }
}
