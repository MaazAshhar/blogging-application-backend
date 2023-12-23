package com.ashhar.blogappapis.payloads;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private UUID postId;
    @NotEmpty
    @Size(min = 4,message = "Title must contain at least 4 characters.")
    private String title;

    private String content;

    private String imageName;

    private String addedDate;

    private CategoryDto category;

    private UserDto user;
    
    private List<CommentDto> comments=new ArrayList<>();
}
