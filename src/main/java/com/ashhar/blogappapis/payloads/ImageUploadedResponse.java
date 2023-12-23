package com.ashhar.blogappapis.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageUploadedResponse {
	private String message;
	private boolean success;
	private PostDto postDto;

}
