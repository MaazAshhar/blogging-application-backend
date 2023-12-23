package com.ashhar.blogappapis.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    UUID categoryId;
    @NotBlank
    @Size(min=4,message = "category title should be minimum of 4 character.")
    String categoryTitle;
    @NotBlank
    @Size(min=10,message = "category description should be minimum of 10 characters.")
    String categoryDescription;
}
