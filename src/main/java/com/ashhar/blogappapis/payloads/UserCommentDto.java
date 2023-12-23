package com.ashhar.blogappapis.payloads;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserCommentDto {
	private UUID id;
	private String name;
}
