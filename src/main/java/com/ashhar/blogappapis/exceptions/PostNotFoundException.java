package com.ashhar.blogappapis.exceptions;

import java.text.MessageFormat;
import java.util.UUID;

public class PostNotFoundException extends RuntimeException{
    private UUID userId;
    private UUID categoryId;
    public PostNotFoundException(UUID userId,UUID categoryId){
        super(MessageFormat.format("No post asociated to user_id: {0} and category_id: {1}",userId,categoryId));
        this.userId=userId;
        this.categoryId=categoryId;
    }
}
